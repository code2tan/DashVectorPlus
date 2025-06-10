package code2t.com.dvp.service;

import org.springframework.stereotype.Service;

@Service
public class DashVectorService {

    private final DashVectorInit dashVectorInit;

    public DashVectorService(DashVectorInit dashVectorInit) {
        this.dashVectorInit = dashVectorInit;
    }
}
