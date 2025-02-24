package com.eazybytes.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor of the application.
     * ( spring security 사용 시 securityContextHolder에 저장된 Authentication 객체에서 
     * 사용자 정보를 가져와서 생성/수정자 id를 reutrun 하도록 함 ) 
     * @return the current auditor.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
      return Optional.of("ACCOUNTS_MS");
    }
	
}
