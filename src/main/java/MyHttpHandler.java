import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;

import java.util.ArrayList;
import java.util.List;

public class MyHttpHandler implements HttpHandler
{
    private final MontoyaApi api;
    private final List<String> uniqueUrlList;

    public MyHttpHandler(MontoyaApi api)
    {
        this.api = api;

        uniqueUrlList = new ArrayList<>();
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
            api.organizer().sendToOrganizer(HttpRequestResponse.httpRequestResponse(responseReceived.initiatingRequest(), responseReceived));
            uniqueUrlList.add(responseReceived.initiatingRequest().url());
        }

        return ResponseReceivedAction.continueWith(responseReceived);
    }

    private boolean matchesCriteria(HttpResponseReceived responseReceived)
    {
        return responseReceived.toolSource().isFromTool(ToolType.PROXY, ToolType.REPEATER, ToolType.TARGET) && api.scope().isInScope(responseReceived.initiatingRequest().url()) && !uniqueUrlList.contains(responseReceived.initiatingRequest().url());
    }
}
