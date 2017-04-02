/*******************************************************************************
 * @author Stefan Gündhör <stefan@guendhoer.com>
 *
 * @copyright Copyright (c) 2017, Stefan Gündhör <stefan@guendhoer.com>
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
package org.ownchan.server.ui.core.config;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.ownchan.server.ui.core.ScanBaseMarker;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration("ownchan-server-ui-core-config")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String implementationVersion = ScanBaseMarker.class.getPackage().getImplementationVersion();

    String pathVersion;
    CacheControl cacheControl;
    if (StringUtils.isNotBlank(implementationVersion)) {
      /*
       * As we use versioning for the GUI URL paths, we can cache "forever".
       * Browsers will re-validate when the resource's URL changes with every new version of the application.
       */
      cacheControl = CacheControl.maxAge(365, TimeUnit.DAYS);
      pathVersion = implementationVersion;
    } else {
      /*
       * If the application is started directly in the DEV IDE,
       * we don't cache and allow all versions in the path.
       */
      cacheControl = CacheControl.noStore();
      pathVersion = "{version:[a-zA-Z0-9\\.\\-]+}";
    }

    String webBasePath = "/ocn-ui-core/" + pathVersion;
    String localBasePath = "classpath:/ownchan-server-ui-core";

    registry.addResourceHandler(webBasePath + "/widgets/**")
        .addResourceLocations(localBasePath + "/jqx/jqwidgets/")
        .setCacheControl(cacheControl);
    registry.addResourceHandler(webBasePath + "/common/**")
        .addResourceLocations(localBasePath + "/common/")
        .setCacheControl(cacheControl);
  }

}
