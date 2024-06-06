package com.gooplanycol.gooplany.application.ports.output;


public interface EmailInputPort {

    void send(String to, String email);
}
