module com.botlabs.scripts.module {
    requires jakarta.ws.rs;

    requires org.glassfish.jersey.container.servlet;
    requires org.glassfish.jersey.inject.hk2;

    exports com.botlabs.scripts;
}