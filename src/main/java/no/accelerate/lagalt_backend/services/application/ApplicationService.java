package no.accelerate.lagalt_backend.services.application;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.services.CrudService;



public interface ApplicationService extends CrudService<Application, Integer> {
    public void accept();
    public void deny();
}
