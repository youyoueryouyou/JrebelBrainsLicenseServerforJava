package com.vvvtimes.server;

import com.vvvtimes.JrebelUtil.JrebelSign;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class MainServer extends AbstractHandler {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8888);
        server.setHandler(new MainServer());
        server.start();
        server.join();
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (target.equals("/")) {
            indexHandler(target, baseRequest, request, response);
        }  else if (target.equals("/jrebel/jrebel/validate-connection")) {
            jrebelLeasesHandler(target, baseRequest, request, response);
        }  else if (target.equals("/agent/leases/validate-connection")) {
            jrebelLeasesHandler(target, baseRequest, request, response);
        } else {
            jrebelLeasesHandler(target, baseRequest, request, response);
            //response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

    }

    private void jrebelLeasesHandler(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String clientRandomness = request.getParameter("randomness");
        if (clientRandomness == null){
            UUID uuid=UUID.randomUUID();
            String str = uuid.toString();
            String uuidStr =str.replace("-", "").substring(0,12);
            clientRandomness = uuidStr;
        }
        String username = request.getParameter("username");
        String guid = request.getParameter("guid");
        baseRequest.setHandled(true);
        String jsonStr = "{\n" +
                "    \"serverVersion\": \"3.2.4\",\n" +
                "    \"serverProtocolVersion\": \"1.1\",\n" +
                "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n" +
                "    \"groupType\": \"managed\",\n" +
                "    \"id\": 1,\n" +
                "    \"licenseType\": 1,\n" +
                "    \"evaluationLicense\": false,\n" +
                "    \"signature\": \"OJE9wGg2xncSb+VgnYT+9HGCFaLOk28tneMFhCbpVMKoC/Iq4LuaDKPirBjG4o394/UjCDGgTBpIrzcXNPdVxVr8PnQzpy7ZSToGO8wv/KIWZT9/ba7bDbA8/RZ4B37YkCeXhjaixpmoyz/CIZMnei4q7oWR7DYUOlOcEWDQhiY=\",\n" +
                "    \"serverRandomness\": \"H2ulzLlh7E0=\",\n" +
                "    \"seatPoolType\": \"standalone\",\n" +
                "    \"statusCode\": \"SUCCESS\",\n" +
                "    \"offline\": false,\n" +
                "    \"validFrom\": null,\n" +
                "    \"validUntil\": null,\n" +
                "    \"company\": \"Administrator\",\n" +
                "    \"orderId\": \"\",\n" +
                "    \"zeroIds\": [\n" +
                "        \n" +
                "    ],\n" +
                "    \"licenseValidFrom\": 1490544001000,\n" +
                "    \"licenseValidUntil\": 1691839999000\n" +
                "}";

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        if (clientRandomness == null || username == null || guid == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            JrebelSign jrebelSign =new JrebelSign();
            jrebelSign.toLeaseCreateJson(clientRandomness,guid);
            String signature = jrebelSign.getSignature();
            jsonObject.put("signature",signature);
            jsonObject.put("company",username);
            String body = jsonObject.toString();
            response.getWriter().print(body);
        }
    }

    private void indexHandler(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello,This is a Jrebel License Server!</h1>");

    }
}