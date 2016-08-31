package util;

/**
 * Created by Russell on 2/9/2016.
 */
public abstract class Task {
    public abstract boolean validate();

    public abstract void execute();

    public abstract int priority();

    public abstract String getName();
}