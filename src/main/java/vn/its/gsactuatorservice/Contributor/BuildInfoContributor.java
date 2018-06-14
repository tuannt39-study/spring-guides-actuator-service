package vn.its.gsactuatorservice.Contributor;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BuildInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> data = new HashMap<>();
        data.put("build.version", "2.0.2.RELEASE");
        builder.withDetail("buildInfo", data);
    }

}