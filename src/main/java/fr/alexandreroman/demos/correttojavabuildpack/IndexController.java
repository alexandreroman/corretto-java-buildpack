/*
 * Copyright (c) 2018 Pivotal Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.alexandreroman.demos.correttojavabuildpack;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
public class IndexController {
    private final AccessLogRepository repo;

    public IndexController(final AccessLogRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String getIndex(HttpServletRequest req, Model model) {
        final String userAgent = req.getHeader("User-Agent");
        if (userAgent != null) {
            final AccessLog log = new AccessLog();
            log.setTimestamp(new Date());
            log.setUserAgent(userAgent);
            repo.save(log);
        }

        final PageRequest pageReq = PageRequest.of(0, 10, Sort.by("timestamp").descending());
        final Page<AccessLog> logs = repo.findAll(pageReq);
        model.addAttribute("logs", logs);

        final SortedMap<String, String> systemProps = new TreeMap(System.getProperties());
        final StringBuilder systemPropsBuf = new StringBuilder(512);
        for (final Map.Entry<String, String> e : systemProps.entrySet()) {
            if (systemPropsBuf.length() != 0) {
                systemPropsBuf.append("\n");
            }
            systemPropsBuf.append(e.getKey()).append("=").append(e.getValue().trim());
        }
        model.addAttribute("systemProps", systemPropsBuf);

        return "index";
    }
}
