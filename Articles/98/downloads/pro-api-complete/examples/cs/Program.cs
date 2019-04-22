using com.aimms.pro.api;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;

namespace ProAppExample
{
    class Program
    {
        // location of where the PRO server is
        public static String PRO_ENDPOINT = "wss://aimms-sandbox.cloud.aimms.com";
        // public static String PRO_ENDPOINT = "ws://my.pro.com";
        // public static String PRO_ENDPOINT = "ws://my.pro.com:8080";

        // alternative you can use the endpoint below if the C# application
        // is running on the same machine on which the PRO services are hosted:
        // public static String PRO_ENDPOINT = "tcp://localhost:19340";

        // some login information
        public static String PRO_ENVIRONMENT = "root";
        public static String USERNAME = "theo";
        public static String USER_PASSWORD = "l4VAvyI!!Axs";


        // the application name and version with which the API example was published
        public static String PRO_APPLICATION_NAME = "PROApiExampleApplication";
        public static String PRO_APPLICATION_VERSION = "2";


        static void Main(string[] args)
        {
            ServerConfig config = new ServerConfig(USERNAME, USER_PASSWORD, PRO_ENVIRONMENT, PRO_ENDPOINT);

            Server server = null;

            try
            {
                server = Server.createConnection(config);

                // shows how to schedule a new job
                BasicScheduleJob(server);

                // example showing how to retrieve information about the current jobs (finished/running and pending)
                ListAllJobs(server);

                // shows how to schedule a new job and wait for it to finish
                ScheduleJobAndCheck(server);

                // shows how to schedule a new job that sends some results
                // back to this side (using DelegateToClient in AIMMS)
                ScheduleJobAndCheckResult(server);

                // shows how to create multidimensional parameters
                CreateMultidimensionalParameter();

                // shows how to start a job in which you have control over
                // the flow of the remote job
                AdvancedInteraction(server);

                // shows how to re-attach to the previously scheduled job with the BasicScheduleJob
                // to check it's results
                ReAttachJob(server);
				
				// shows how to upload/download a file to/from PRO Storage
                UploadAndDownLoadAndDeleteFile(server);

            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            finally
            {
                if (server != null)
                {
                    server.closeConnection();
                }
            }

        }

        private static void CreateMultidimensionalParameter()
        {
            // create a new two dimensional parameter
            Parameter transport = new Parameter(2);

            transport.add(new string[] { "New York", "Rome" }, 235.0);
            transport.add(new string[] { "Berlin", "Washington" }, 385.0);
            transport.add(new string[] { "Amsterdam", "Paris" }, 965.0);
            transport.add(new string[] { "New York", "Paris" }, 37.0);

            // iterate over the transport data
            foreach (TupleValuePair<double> kv in transport.Data)
            {
                Console.WriteLine(kv);
            }
        }

        static void ListAllJobs(Server server)
        {
            Console.WriteLine("Current jobs");

            // get the current list of jobs
            Job[] jobs = server.getCurrentJobs();
            foreach (Job job in jobs)
            {
                Console.WriteLine("App={0}, Description={1}, Status={2}, Owner={3}, Created={4}, RunTime={5}, QueueTime={6}",
                    job.App, job.Description, job.Status, job.Owner, job.Created, job.RunTime, job.QueueTime);
            }
        }

        static void BasicScheduleJob(Server server)
        {
            // This example will queue a job at the server that starts the example application and runs the BasicExample procedure
            ProcedureCall procedureCall = new ProcedureCall("proc_BasicScheduleJob", new Parameter(100.0), new StringParameter("text"));

            // additionally we specify we want to run it one minute from now; by default PRO will try to run it immediately
            DateTime scheduleAt = DateTime.Now;
            scheduleAt += TimeSpan.FromMinutes(1);

            // further we specify the timeout for the job as 1 hour
            JobConfig jobConfig = new JobConfig(PRO_APPLICATION_NAME, PRO_APPLICATION_VERSION, "BasicScheduleJob", TimeSpan.FromHours(1), scheduleAt, procedureCall);

            // schedule it at the PRO server
            server.scheduleJob(jobConfig);

        }

        static void ScheduleJobAndCheck(Server server)
        {
            // in this example we will schedule the same job as with the basic-example, but using default values
            ProcedureCall procedureCall = new ProcedureCall("proc_ScheduleJobAndCheck", new Parameter(200.0), new StringParameter("some other text"));
            JobConfig jobConfig = new JobConfig(PRO_APPLICATION_NAME, PRO_APPLICATION_VERSION, "ScheduleJobAndCheck", procedureCall);

            Job job = server.scheduleJob(jobConfig);
            // let's wait for the job to finish
            while (!job.hasEnded())
            {
                Console.WriteLine("Waiting for job: App={0}, Description={1}, Status={2}, Owner={3}, Created={4}, RunTime={5}, QueueTime={6}",
                    job.App, job.Description, job.Status, job.Owner, job.Created, job.RunTime, job.QueueTime);
                // do some stuff ... emulate by sleeping
                System.Threading.Thread.Sleep(1000);

                // retrieve the latest info from server again, because
                // the state might have changed
                job.refresh();
            }
            Console.WriteLine("Ended: App={0}, Description={1}, Status={2}, Owner={3}, Created={4}, RunTime={5}, QueueTime={6}",
                job.App, job.Description, job.Status, job.Owner, job.Created, job.RunTime, job.QueueTime);
        }

        static void ScheduleJobAndCheckResult(Server server)
        {
            // This example is similar to the ScheduleJobAndCheck, but the interaction is now more fine-grained
            ProcedureCall procedureCall = new ProcedureCall("proc_ScheduleJobAndCheckResult", new Parameter(300.0), new StringParameter("new text"));
            JobConfig jobConfig = new JobConfig(PRO_APPLICATION_NAME, PRO_APPLICATION_VERSION, "ScheduleJobAndCheckResult", procedureCall);

            // create a new instance of the JobInteractor, which allows us to interact with the job
            JobInteractor jobInteractor = new JobInteractor();
            Job job = server.scheduleJob(jobConfig, jobInteractor);

            InteractLoop(job, jobInteractor);
        }

        static void ReAttachJob(Server server)
        {
            // get the current list of jobs
            Job[] jobs = server.getCurrentJobs();

            if (jobs.Length > 0)
            {
                // let's re-attach on the job we created with the BasicScheduleJob
                // (it was scheduled on 1 minute from its submission time)
                Job existingJob = jobs[0];
                for (int i = 0; i < jobs.Length; ++i)
                {
                    if (jobs[i].Description == "BasicScheduleJob" && !jobs[i].hasEnded())
                    {
                        existingJob = jobs[i];

                    }
                }

                Console.WriteLine("Reattaching to: {0}", existingJob);

                JobInteractor existingJobInteractor = new JobInteractor();
                existingJob.JobInteractor = existingJobInteractor;

                // and do the same interaction loop as the one from the ScheduleJobAndCheckResult
                InteractLoop(existingJob, existingJobInteractor);
            }

        }


        static void InteractLoop(Job job, JobInteractor jobInteractor)
        {
            // This is an example main-loop that will wait for events to happen on the remote job.
            bool done = false;
            bool finishHappened = false;
            do
            {
                // wait for some while to capture events from the pending job
                InteractiveJobEvent ev = jobInteractor.waitForEvent(TimeSpan.FromSeconds(1));

                // because it is possible that events arrive in a different order in which they happened remotely,
                // we will wait one more time for incoming ProcedureCalls. This is done by setting 'finishHappened'
                // to true at the Finished event below. When we waited one time for other potential incoming messages, 
                // after receiving the finish event, we deem this job really done.
                if (finishHappened)
                {
                    done = true;
                }
                switch (ev)
                {
                    case InteractiveJobEvent.TimeOut:
                        // the specified amount of time (1 second) expired, do some other stuff
                        Console.WriteLine("Waiting for events on job");
                        break;

                    case InteractiveJobEvent.Error:
                        string lastError = jobInteractor.firstIncomingError();
                        Console.WriteLine("error: {0}", lastError);
                        // when we get errors, just terminate
                        job.terminate();
                        done = true;
                        break;

                    case InteractiveJobEvent.ProcedureCall:
                        ProcedureCall lastProcedureCall = jobInteractor.firstIncomingProcedureCall();
                        Console.WriteLine("procedureCall: {0}", lastProcedureCall.ProcedureName);

                        // show what we retrieved
                        DisplayProcedureCall(lastProcedureCall);
                        break;

                    case InteractiveJobEvent.Finished:
                        Console.WriteLine("job Finished");
                        finishHappened = true;
                        break;
                }
            } while (!done);
        }

        private static void DisplayProcedureCall(ProcedureCall lastProcedureCall)
        {
            for (int i = 0; i < lastProcedureCall.ProcedureArguments.Count; i++)
            {
                Identifier id = lastProcedureCall.ProcedureArguments[i];
                switch (id.IdentifierType)
                {
                    case IdentifierType.Parameter:
                        Parameter par = (Parameter)id;
                        Console.WriteLine(" argument[{0}], Parameter:", i);
                        foreach (TupleValuePair<double> tv in par.Data)
                        {
                            Console.WriteLine("  {0} : {1}", tv.tuple, tv.value);
                        }
                        break;
                    case IdentifierType.StringParameter:
                        StringParameter stringPar = (StringParameter)id;
                        Console.WriteLine(" argument[{0}], StringParameter:", i);
                        foreach (TupleValuePair<string> tv in stringPar.Data)
                        {
                            Console.WriteLine("  {0} : '{1}'", tv.tuple, tv.value);
                        }
                        break;
                }
            }
        }


        static void AdvancedInteraction(Server server)
        {
            // This example is similar to the ScheduleJobAndCheckResult, but the interaction is now more fine-grained
            ProcedureCall procedureCall = new ProcedureCall("proc_AdvancedInteraction", new Parameter(3.14), new StringParameter("abacadabra"));
            JobConfig jobConfig = new JobConfig(PRO_APPLICATION_NAME, PRO_APPLICATION_VERSION, "AdvancedInteraction", procedureCall);

            // create a new instance of the JobInteractor, which allows us to interact with the job
            JobInteractor jobInteractor = new JobInteractor();
            Job job = server.scheduleJob(jobConfig, jobInteractor);

            AdvancedInteractLoop(job, jobInteractor);
        }

        static void AdvancedInteractLoop(Job job, JobInteractor jobInteractor)
        {
            bool done = false;
            bool finishHappened = false;
            int numberOfWaits = 0;
            do
            {
                // wait for some while to capture events from the pending job
                InteractiveJobEvent ev = jobInteractor.waitForEvent(TimeSpan.FromSeconds(1));

                // because it is possible that events arrive in a different order in which they happened remotely,
                // we will wait one more time for incoming ProcedureCalls. This is done by setting 'finishHappened'
                // to true at the Finished event below. When we waited one time for other potential incoming messages, 
                // after receiving the finish event, we deem this job really done.
                if (finishHappened)
                {
                    done = true;
                }
                switch (ev)
                {
                    case InteractiveJobEvent.TimeOut:
                        // show some status
                        Console.WriteLine("Waiting for events on job");
                        numberOfWaits++;
                        // after waiting some time, inject a procedureCall to give us some progress updates
                        // the AdvancedInteractProgress procedure sends (by issuing a DelegateToClient)
                        // the desired progress updates, after that it continues where it left of the main
                        // procedure 
                        if (numberOfWaits % 5 == 0)
                        {
                            Console.WriteLine("Requesting progress report");
                            ProcedureCall progressCall = new ProcedureCall("proc_AdvancedInteractProgress");
                            jobInteractor.inject(progressCall);
                        }
                        break;

                    case InteractiveJobEvent.Error:
                        string lastError = jobInteractor.firstIncomingError();
                        Console.WriteLine("error: {0}", lastError);
                        // when we get errors, just terminate
                        job.terminate();
                        done = true;
                        break;

                    case InteractiveJobEvent.ProcedureCall:
                        ProcedureCall lastProcedureCall = jobInteractor.firstIncomingProcedureCall();
                        Console.WriteLine("procedureCall: {0}", lastProcedureCall.ProcedureName);

                        // since we know we're only going to be called with one (scalar) parameter, 
                        // take some shortcuts here:
                        Parameter p = (Parameter)lastProcedureCall.ProcedureArguments[0];
                        double fractionCompleted = p.Data[0].value;

                        // let's check whether we think it's ok
                        if (fractionCompleted > .75)
                        {
                            Console.WriteLine("Percentage completed = {0}%, enough, so stopping the current tast", fractionCompleted * 100.0);
                            jobInteractor.stopTask();

                            // alternatively we might just stop the solver only, and continue our normal flow
                            // for this example the solver is not running
                            // jobInteractor.stopSolve();
                        }
                        else
                        {
                            Console.WriteLine("Percentage completed = {0}%, not enough, continueing", fractionCompleted * 100.0);
                        }
                        break;

                    case InteractiveJobEvent.Finished:
                        Console.WriteLine("job Finished");
                        finishHappened = true;
                        break;
                }
            } while (!done);
        }
		

        private static void UploadAndDownLoadAndDeleteFile(Server server)
        {
            // change this value to point to your pro-api-storage-example-input.xml file location
            String fileLocation = "c:\\Temp\\files";
            // Storage path. Can be any location in the storage that is available for write for your user
            String storagePath = "userdata" + Path.DirectorySeparatorChar + PRO_ENVIRONMENT + Path.DirectorySeparatorChar + USERNAME + Path.DirectorySeparatorChar + "storage" + Path.DirectorySeparatorChar + "example";

            String originalFile = fileLocation + Path.DirectorySeparatorChar + "pro-api-storage-example-input.xml";
            String downloadedFile = fileLocation + Path.DirectorySeparatorChar + "pro-api-storage-example-output.xml";

            Console.WriteLine("Original text: " + File.ReadAllText(originalFile));

            Console.WriteLine("Uploading the file...");
            server.uploadLocalFileToStorage(originalFile, storagePath);

            Console.WriteLine("Executing the job...");
            executeDownloadAndUpload(server, storagePath);

            Console.WriteLine("Downloading the file...");
            server.downloadStorageFileToLocalFile(storagePath, downloadedFile);

            String downloadedContent = File.ReadAllText(downloadedFile);
            Console.WriteLine("Text in the file: " + downloadedContent);
			
            Console.WriteLine("Deleting the file...");
            server.deleteFileFromStorage(storagePath);
        }

        private static void executeDownloadAndUpload(Server server, String storagePath)
        {
            ProcedureCall procedureCall = new ProcedureCall("DownloadAndUpload", new StringParameter(storagePath));
            JobConfig jobConfig = new JobConfig(PRO_APPLICATION_NAME, PRO_APPLICATION_VERSION, "DownloadAndUpload", procedureCall);

            Job job = server.scheduleJob(jobConfig);
            // let's wait for the job to finish
            while (!job.hasEnded())
            {
                Console.Write("Waiting for job: App={0}, Description={1}, Status={2}, Owner={3}, Created={4}, RunTime={5}, QueueTime={6}",
                        job.App, job.Description, job.Status, job.Owner, job.Created, job.RunTime, job.QueueTime);

                // do some stuff ... emulate by sleeping
                Thread.Sleep(1000);

                // retrieve the latest info from server again, because
                // the state might have changed
                job.refresh();
            }
        }
    }
}
