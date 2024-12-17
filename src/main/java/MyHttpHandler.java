import burp.api.montoya.http.handler.*;
import burp.api.montoya.organizer.Organizer;
import burp.api.montoya.persistence.PersistedList;

import static burp.api.montoya.core.ToolType.*;
import static burp.api.montoya.http.message.HttpRequestResponse.httpRequestResponse;

public class MyHttpHandler implements HttpHandler
{
    private final Organizer organizer;
    private final PersistedList<String> uniqueUrlList;

    public MyHttpHandler(Organizer organizer, PersistedList<String> uniqueUrlList)
    {
        this.organizer = organizer;
        this.uniqueUrlList = uniqueUrlList;
    }

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent)
    {
        return RequestToBeSentAction.continueWith(requestToBeSent);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived)
    {
        if (matchesCriteria(responseReceived))
        {
            organizer.sendToOrganizer(httpRequestResponse(responseReceived.initiatingRequest(), responseReceived));
            uniqueUrlList.add(responseReceived.initiatingRequest().url());
        }

        return ResponseReceivedAction.continueWith(responseReceived);
    }

    private boolean matchesCriteria(HttpResponseReceived responseReceived)
    {
        return responseReceived.toolSource().isFromTool(PROXY, REPEATER, TARGET)
                && responseReceived.initiatingRequest().isInScope()
                && !uniqueUrlList.contains(responseReceived.initiatingRequest().url());
    }
}
