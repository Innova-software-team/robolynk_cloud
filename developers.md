### Safe development
As we've made the decision to combine some of the microservices into a single server application, to ensure we still see 
strong isolation between services, services should NOT read/write parts of the database that do not belong to them.

Rules: 
- services MUST NOT write/read any part of a database that does not belong to them (issues in gitlab give more 
  info about what belongs to which service) 
- 
