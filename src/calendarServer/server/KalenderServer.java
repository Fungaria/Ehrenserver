/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarServer.server;

import calendarServer.server.listeners.LoginListener;
import calendarServer.server.listeners.TerminListener;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import calendarServer.database.*;
import calendarServer.server.NetworkData.*;
import calendarServer.server.listeners.BlockListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sreis
 */
public class KalenderServer extends Server {

    private final int TIMEOUT = 5000;
    private final int TCP_PORT = 54777;
    private final int UDP_PORT = 54779;

    public KalenderServer() {
        registerClasses();
        registerListeners();
    }

    public void startServer() {
        super.start();
        try {
            super.bind(TCP_PORT, UDP_PORT);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void registerClasses() {
        Kryo k = getKryo();

        k.register(ArrayList.class);
        k.register(DataRoot.class);
        k.register(Date.class);
        k.register(Kunde.class);
        k.register(Friseur.class);
        k.register(Service.class);
        k.register(Termin.class);
        k.register(Blockierung.class);
        k.register(LoginRequest.class);
        k.register(LoginResponse.class);
        k.register(TerminRequest.class);
        k.register(StornoRequest.class);
        k.register(BlockRequest.class);
        k.register(RemoveBlockRequest.class);
    }

    private void registerListeners() {
        super.addListener(new TerminListener());
        super.addListener(new LoginListener());
        super.addListener(new BlockListener());
    }
}