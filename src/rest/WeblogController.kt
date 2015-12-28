package ledg.rest
import ledg.Entity.WeblogEntry
import ledg.Repository.WeblogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
//@RequestMapping(value = "/log")
class WeblogController{

    @Autowired var weblogRepo : WeblogRepository? = null

    @RequestMapping(value = "/log",params = arrayOf("ip","uri","duration"),method = arrayOf( RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody
    fun log(@RequestParam("ip") ip : String,@RequestParam("uri") uri:String,@RequestParam("duration") duration:  String,
    request : HttpServletRequest) : String
    {
        //println("ip: ${ip} uri ${uri} duration ${duration}")

        val path=uri.split("/")
        if (path[1]=="css")
            return ""

        var status=0
        if (ip in arrayOf("77.38.245.103","127.0.0.1","194.9.175.33","195.13.247.67"))
            status=1


        with (WeblogEntry())
        {
            this.ip=ip
            this.duration=Integer.parseInt(duration)
            this.entryDate= Date(System.currentTimeMillis())
            this.controller=path.getOrNull(1)
            this.queryObj=path.getOrNull(2)
            this.status=status

            try {
                this.page = Integer.parseInt(path.getOrNull(3))
            }
            catch (e : Exception)
            {
                this.page=0
            }
            weblogRepo?.save(this)
        }

        return ""
    }
}