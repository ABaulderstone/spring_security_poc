package com.example.securitypoc.common;

public sealed abstract class Either<L, R> permits Either.Left, Either.Right {

    public abstract boolean isLeft();

    public abstract boolean isRight();

    public abstract L getLeft();

    public abstract R getRight();

    public static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    private static final class Left<L, R> extends Either<L, R> {
        private final L value;

        private Left(L value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public L getLeft() {
            return this.value;
        }

        @Override
        public R getRight() {
            throw new RuntimeException("Can't call getRight on a Left");
        }

        @Override
        public String toString() {
            return "Left[" + value + "]";
        }
    }

    private static final class Right<L, R> extends Either<L, R> {
        private final R value;

        private Right(R value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public L getLeft() {
            throw new RuntimeException("Can't call getLeft on a Right");
        }

        @Override
        public R getRight() {
            return this.value;
        }

        @Override
        public String toString() {
            return "Right[" + value + "]";
        }
    }
}
