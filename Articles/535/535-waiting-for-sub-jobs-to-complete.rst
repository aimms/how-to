Waiting for sub jobs to complete
=================================

reasons for having sub jobs to a server session
- monte carlo?
- sensitivity?
- 

problem: solver session just executes one (lengthy) procedure.

4. Show information flow in ppt.
5. Analyze app
   scj::pr_waitOnSubJobCompletion - contains "pro::messaging::WaitForMessages('',0,0);"
   Show app structure
   - library structure (main for handling screen, control solver job separate lib, ...)
   - defining inputs / outputs via sections
     - used by pro::ManagedSessionInputCaseIdentifierSet / pro::ManagedSessionOutputCaseIdentifierSet
     - For instance in scj::pr_submitAllSubjobs
