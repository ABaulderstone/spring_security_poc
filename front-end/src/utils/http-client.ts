type CsrfResponse = {
  headerName: string;
  token: string;
  parameterName: string;
};
type FetchOptions = Omit<RequestInit, 'headers'> & {
  headers?: Record<string, string>;
};

export class HttpError extends Error {
  status: number;
  constructor(status: number, message: string) {
    super(message);
    this.name = 'HttpError';
    this.status = status;
  }
}

export class HttpClient {
  private unauthorizedHandler: () => void;
  private API_URL: String;
  constructor(unauthorizedHandler: () => void) {
    this.unauthorizedHandler = unauthorizedHandler;
    this.API_URL = import.meta.env.VITE_API_URL;
  }

  private async getCsrfToken(): Promise<string> {
    const res = await fetch(this.API_URL + '/csrf/csrf-token', {
      credentials: 'include',
    });
    if (!res.ok) {
      throw new HttpError(res.status, 'Failed to get token');
    }
    const data = (await res.json()) as CsrfResponse;
    return data.token;
  }

  private async doFetch<T = unknown>(
    endpoint: string,
    opts: FetchOptions = {},
    includeCsrf = false,
    isFormData = false
  ): Promise<T> {
    const headers: Record<string, string> = {
      ...opts.headers,
    };

    if (!isFormData) {
      headers['Content-Type'] ||= 'application/json';
    }

    if (includeCsrf) {
      const csrfToken = await this.getCsrfToken();
      headers['X-CSRF-TOKEN'] = csrfToken;
    }

    const res = await fetch(this.API_URL + endpoint, {
      ...opts,
      credentials: 'include',
      headers,
    });

    const contentType = res.headers.get('Content-Type');
    const isJsonResponse = contentType?.includes('application/json');
    const hasBody = res.status !== 204 && isJsonResponse;

    if (res.status === 401) {
      this.unauthorizedHandler();
      throw new HttpError(401, 'Unauthorized');
    }

    if (!res.ok) {
      const errBody = await res.json().catch(() => ({}));
      throw new HttpError(res.status, errBody.message || 'Fetch error');
    }

    return hasBody ? (res.json() as T) : (undefined as unknown as T);
  }

  public get<T>(endpoint: string, options?: FetchOptions) {
    return this.doFetch<T>(endpoint, { ...options, method: 'GET' });
  }

  public post<T>(
    endpoint: string,
    body: unknown,
    options?: FetchOptions,
    useCsrf = true
  ) {
    const isFormData = body instanceof FormData;
    return this.doFetch<T>(
      endpoint,
      {
        ...options,
        method: 'POST',
        body: isFormData ? body : JSON.stringify(body),
      },
      useCsrf,
      isFormData
    );
  }
}
