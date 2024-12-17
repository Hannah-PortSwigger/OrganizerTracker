import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.persistence.PersistedList;

@SuppressWarnings("unused")
public class OrganizerTracker implements BurpExtension
{
    private static final String NAME = "Organizer Tracker";

    @Override
    public void initialize(MontoyaApi api)
    {
        api.extension().setName(NAME);

        PersistedList<String> uniqueUrlList = api.persistence().extensionData().getStringList("uniqueUrlList") != null
                ? api.persistence().extensionData().getStringList("uniqueUrlList")
                : PersistedList.persistedStringList();

        if (uniqueUrlList.isEmpty())
        {
            api.persistence().extensionData().setStringList("uniqueUrlList", uniqueUrlList);
        }

        api.http().registerHttpHandler(new MyHttpHandler(api.organizer(), uniqueUrlList));
    }
}