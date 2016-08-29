
package foo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the foo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SayHello_QNAME = new QName("http://foo/", "sayHello");
    private final static QName _EchoBinaryAsString_QNAME = new QName("http://foo/", "echoBinaryAsString");
    private final static QName _SayHelloResponse_QNAME = new QName("http://foo/", "sayHelloResponse");
    private final static QName _EchoBinaryAsStringResponse_QNAME = new QName("http://foo/", "echoBinaryAsStringResponse");
    private final static QName _EchoBinaryAsStringArg0_QNAME = new QName("", "arg0");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: foo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link EchoBinaryAsString }
     * 
     */
    public EchoBinaryAsString createEchoBinaryAsString() {
        return new EchoBinaryAsString();
    }

    /**
     * Create an instance of {@link EchoBinaryAsStringResponse }
     * 
     */
    public EchoBinaryAsStringResponse createEchoBinaryAsStringResponse() {
        return new EchoBinaryAsStringResponse();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://foo/", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoBinaryAsString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://foo/", name = "echoBinaryAsString")
    public JAXBElement<EchoBinaryAsString> createEchoBinaryAsString(EchoBinaryAsString value) {
        return new JAXBElement<EchoBinaryAsString>(_EchoBinaryAsString_QNAME, EchoBinaryAsString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://foo/", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoBinaryAsStringResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://foo/", name = "echoBinaryAsStringResponse")
    public JAXBElement<EchoBinaryAsStringResponse> createEchoBinaryAsStringResponse(EchoBinaryAsStringResponse value) {
        return new JAXBElement<EchoBinaryAsStringResponse>(_EchoBinaryAsStringResponse_QNAME, EchoBinaryAsStringResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = EchoBinaryAsString.class)
    public JAXBElement<byte[]> createEchoBinaryAsStringArg0(byte[] value) {
        return new JAXBElement<byte[]>(_EchoBinaryAsStringArg0_QNAME, byte[].class, EchoBinaryAsString.class, ((byte[]) value));
    }

}
