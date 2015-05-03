package clusterhqminirest;

import clusterhqminirest.service.MessageService;
import restx.factory.Component;
import restx.factory.Module;
import restx.factory.Provides;
import restx.server.WebServer;

/**
 * Created by dreamer on 03/05/15.
 */
@Component
public class ServiceModule
{
        @Provides
        public MessageService getRunningService ()
        {
            return AppServer.localService;
        }
}
