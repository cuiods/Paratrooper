package edu.tsinghua.paratrooper.web.security;

public class AppContext implements AutoCloseable {

    private static final ThreadLocal<String> CURRENT_USER_ID = new ThreadLocal<>();

    public AppContext(String id) {
        CURRENT_USER_ID.set(id);
    }

    @Override
    public void close() throws Exception {
        CURRENT_USER_ID.remove();
    }

    public static String getCurrentUserId() {
        return CURRENT_USER_ID.get();
    }
}
