package edu.tsinghua.paratrooper.web.security;

public class AppContext implements AutoCloseable {

    private static final ThreadLocal<Integer> CURRENT_USER_ID = new ThreadLocal<>();

    public AppContext(int id) {
        CURRENT_USER_ID.set(id);
    }

    @Override
    public void close() throws Exception {
        CURRENT_USER_ID.remove();
    }

    public static int getCurrentUserId() {
        return CURRENT_USER_ID.get();
    }
}
