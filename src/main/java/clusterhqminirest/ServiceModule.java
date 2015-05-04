package clusterhqminirest;

import clusterhqminirest.service.MessageService;
import restx.factory.Component;
import restx.factory.Provides;

/**
 * This component allows REST to access the message service instance.
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
