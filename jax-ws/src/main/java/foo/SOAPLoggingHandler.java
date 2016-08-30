package foo;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/*
 * This simple SOAPHandler will output the contents of incoming
 * and outgoing messages.
 */
public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {

    // change this to redirect output if desired
    private static PrintStream out = System.out;

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        logToSystemOut(smc);
        return true;
    }

    public boolean handleFault(SOAPMessageContext smc) {
        logToSystemOut(smc);
        return true;
    }

    // nothing to clean up
    public void close(MessageContext messageContext) {
    }

    /*
     * Check the MESSAGE_OUTBOUND_PROPERTY in the context
     * to see if this is an outgoing or incoming message.
     * Write a brief message to the print stream and
     * output the message. The writeTo() method can throw
     * SOAPException or IOException
     */
    private void logToSystemOut(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean)
            smc.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        


        if (outboundProperty.booleanValue()) {
            out.println("\nOutbound message:");
            out.println("レスポンスヘッダー");
            Map<String, List<String>> responseHeaders = (Map<String, List<String>>) smc
                    .get(SOAPMessageContext.HTTP_RESPONSE_HEADERS);
            if (responseHeaders.isEmpty()) {
            	//-Dcom.sun.xml.ws.transport.http.HttpAdapter.dump=true
            	//http://stackoverflow.com/questions/13285342/log-jax-ws-http-request-and-response
            	System.out.println("からです！");
            }
            for (Map.Entry<String, List<String>> entry: responseHeaders.entrySet()) {
    			out.print(entry.getKey()+" : ");
    			for (String value : entry.getValue()) {
    				out.print(value+",");
    			}
    			out.println();
    		}
            
        } else {
            
        	out.println("\nInbound message:");
            out.println("リクエストヘッダー");
            Map<String, List<String>> responseHeaders = (Map<String, List<String>>) smc
                    .get(SOAPMessageContext.HTTP_REQUEST_HEADERS);
            for (Map.Entry<String, List<String>> entry: responseHeaders.entrySet()) {
    			out.print(entry.getKey()+" : ");
    			for (String value : entry.getValue()) {
    				out.print(value+",");
    			}
    			out.println();
    		}
        }

        SOAPMessage message = smc.getMessage();
        try {
            message.writeTo(out);
            out.println("");   // just to add a newline
        } catch (Exception e) {
            out.println("Exception in handler: " + e);
        }
    }
}