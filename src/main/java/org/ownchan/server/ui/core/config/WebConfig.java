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

import org.ownchan.server.ui.core.ScanBaseMarker;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration("ownchan-server-ui-core-config")
public class WebConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String implementationVersion = ScanBaseMarker.class.getPackage().getImplementationVersion();
    /*
     * Locally, all versions are tolerated in the URL (because if the application is not started as a .jar, implementationVersion will be null),
     * while in production environments the version of the dependency will be enforced.
     */
    String webBasePath = "/oc-ui-core/" + (implementationVersion == null ? "{version:[a-zA-Z0-9\\.\\-]+}" : implementationVersion);
    String localBasePath = "classpath:/ownchan-server-ui-core";

    registry.addResourceHandler(webBasePath + "/widgets/**")
        .addResourceLocations(localBasePath + "/jqx/jqwidgets/");
    registry.addResourceHandler(webBasePath + "/common/**")
        .addResourceLocations(localBasePath + "/common/");
  }

}
