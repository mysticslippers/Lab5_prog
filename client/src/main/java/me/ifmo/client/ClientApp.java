package me.ifmo.client;

import java.util.Objects;

public class ClientApp {
    public static void main(String[] args){
        if(Objects.equals(args[0], "CollectionCSV.csv") || Objects.equals(args[0], "CollectionJSON.json") || Objects.equals(args[0], "CollectionXML.xml")){
            ClientLaunchManager clientLaunchManager = new ClientLaunchManager();
            clientLaunchManager.setFilePath(args[0]);
            clientLaunchManager.launch();
        }
    }
}
