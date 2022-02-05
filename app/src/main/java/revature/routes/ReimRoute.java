package revature.routes;

import io.javalin.Javalin;
import revature.Controller.ReimController;

public class ReimRoute extends Route{

    private ReimController rc;

    public ReimRoute(ReimController rc){
        this.rc=rc;
    }

    @Override
    public void registerLocalRoutes(Javalin app) {
        // app.get("/list/employee", uc.getAll);
        // app.get("/list/manager", uc.getAll);
        app.get("/reim", rc.getAll);
        app.get("/reim/{id}", rc.getById);

        app.post("/employee/reim/request", rc.create);
        app.post("/manager/reim/update", rc.update);
        app.post("/manager/reim/validate", rc.validate);
        
    }
    
}
