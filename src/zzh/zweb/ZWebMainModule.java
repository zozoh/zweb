package zzh.zweb;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@SetupBy(ZWebSetup.class)
@IocBy(type = ComboIocProvider.class,
        args = {"*org.nutz.ioc.loader.json.JsonLoader",
                "ioc",
                "*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
                "zzh.zweb"})
@Modules(scanPackage = true, packages = "zzh.zweb.module")
@Localization("msg")
public class ZWebMainModule {

    @At("/version")
    @Ok("jsp:jsp.show_text")
    public String version() {
        return "1.0";
    }

    @At("/event")
    public void onEvent(HttpServletRequest req, HttpServletResponse resp) {
        try {

            resp.setContentType("text/event-stream");
            PrintWriter pw = resp.getWriter();
            // pw.write("Will send event ...\n");
            String[] etps = Lang.array("message", "-zozoh-");

            int n = R.random(1, 10);
            String str = Times.sDTms2(Times.now());

            System.out.printf("It will output %d events: (%s)\n", n, str);
            pw.write("data: it will gen " + n + " events\n\n");
            for (int i = 0; i < n; i++) {
                String etp = etps[R.random(0, 1)];
                String id = R.UU16();

                int retry = R.random(1, 8) * 1000;
                pw.write("id:" + id + "\n");
                if (null != etp)
                    pw.write("event:" + etp + "\n");
                pw.write("retry:" + retry + "\n");
                pw.write("data:[" + etp + "] " + i + "::" + str + "\n\n");

                System.out.printf("  %2d >>%8s(%d) : %s : %s\n",
                                  i,
                                  etp,
                                  retry,
                                  id,
                                  str);
                Thread.sleep(2000);
                pw.flush();
            }
            System.out.printf("-----------%d events sent-------------pw.close()\n\n",
                              n);
            pw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
