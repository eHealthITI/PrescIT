package com.example.graphdb;

import io.github.cdimascio.dotenv.Dotenv;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;

public class Connector {
    private static RemoteRepositoryManager manager;
    private static Repository dbRepo;
    private static RepositoryConnection dbConn;

    public static void initRepo(){
        Dotenv dotenv = Dotenv.load();
        // initiate a remote repo manager
        manager = new RemoteRepositoryManager(dotenv.get("GRAPHDB_URL"));
        manager.init();
        manager.setUsernameAndPassword(dotenv.get("GDB_USER"), dotenv.get("GDB_PWD"));
        // instantiate repo
        dbRepo = manager.getRepository(dotenv.get("DB_REPOSITORY_ID"));
        // connect to the repo
        dbConn = dbRepo.getConnection();
    }

    public static void closeDBConn(){ dbConn.close(); }
    public static RepositoryConnection getDBRepoConnection() { return dbConn; }
}
