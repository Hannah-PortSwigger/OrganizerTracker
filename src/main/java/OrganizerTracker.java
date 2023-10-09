import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.ui.menu.BasicMenuItem;
import burp.api.montoya.ui.menu.Menu;

import javax.swing.*;

public class OrganizerTracker implements BurpExtension
{
    private static final String NAME = "Organizer Tracker";

    @Override
    public void initialize(MontoyaApi api)
    {
        api.extension().setName(NAME);

        api.http().registerHttpHandler(new MyHttpHandler(api));
    }
}