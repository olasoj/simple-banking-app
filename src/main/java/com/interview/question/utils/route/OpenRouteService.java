package com.interview.question.utils.route;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenRouteService {
      public static List<OpenRouteModel> getOpenRoutes() {
            List<OpenRouteModel> openRoutes = new ArrayList<>();
            for (var openRoute : OpenRoutes.values())
                  openRoutes.add(new OpenRouteModel(openRoute.getRoute(), openRoute.getMethods()));

            return openRoutes;
      }

      public static boolean isRouteOpen(String route, String httpMethod) {
            return getOpenRoutes().stream().anyMatch(
                    openRouteModel -> route.startsWith(openRouteModel.getRoute())
                            && openRouteModel.getMethods().contains(httpMethod)
            );
      }
}
