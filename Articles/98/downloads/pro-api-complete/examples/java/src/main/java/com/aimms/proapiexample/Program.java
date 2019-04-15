package com.aimms.proapiexample;

import com.aimms.pro.api.config.JobConfig;
import com.aimms.pro.api.config.ProcedureCall;
import com.aimms.pro.api.config.ServerConfig;
import com.aimms.pro.api.constant.InteractiveJobEvent;
import com.aimms.pro.api.impl.Job;
import com.aimms.pro.api.impl.JobInteractor;
import com.aimms.pro.api.impl.Server;
import com.aimms.pro.api.parameter.Identifier;
import com.aimms.pro.api.parameter.Parameter;
import com.aimms.pro.api.parameter.StringParameter;
import com.aimms.pro.api.parameter.TupleValuePair;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Locale;

public class Program {

    // location of where the PRO server is
    // alternative you can use the endpoint below if the Java application
    // is running on the same machine on which the PRO services are hosted:
    // private static final String PRO_ENDPOINT = "tcp://localhost:19340";
    private static final String DEFAULT_ENDPOINT = "wss://aimms-sandbox.cloud.aimms.com";

    // some login information
    private static final String DEFAULT_ENVIRONMENT = "root";
    private static final String DEFAULT_USERNAME = "theo";
    private static final String DEFAULT_PASSWORD = "l4VAvyI!!Axs";

    // the application name and version with which the API example was published
    private static final String DEFAULT_APPLICATION_NAME = "PROApiExampleApplication";
    private static final String DEFAULT_APPLICATION_VERSION = "2";

    private String endpoint;
    private String environment;
    private String username;
    private String password;
    private String applicationName;
    private String applicationVersion;

    private static Logger logger = LoggerFactory.getLogger(Program.class);

    private Program(String[] args)  {
        parseArgs(args);
    }

    public static void main(String[] args) {
        Program program = new Program(args);


        // TODO supply config through arguments
        ServerConfig config = new ServerConfig(program.username, program.password, program.environment, program.endpoint);
        Server server = null;

        try {
            server = Server.createConnection(config);

            // shows how to schedule a new job
            BasicScheduleJob(server, program.applicationName, program.applicationVersion);

            // example showing how to retrieve information about the current jobs (finished/running and pending)
            ListAllJobs(server);

            // shows how to schedule a new job and wait for it to finish
            ScheduleJobAndCheck(server, program.applicationName, program.applicationVersion);

            // shows how to schedule a new job that sends some results
            // back to this side (using DelegateToClient in AIMMS)
            ScheduleJobAndCheckResult(server, program.applicationName, program.applicationVersion);

            // shows how to create multidimensional parameters
            CreateMultidimensionalParameter();

            // shows how to start a job in which you have control over
            // the flow of the remote job
            AdvancedInteraction(server, program.applicationName, program.applicationVersion);

            // shows how to re-attach to the previously scheduled job with the BasicScheduleJob
            // to check it's results
            ReAttachJob(server);

            // shows how to upload/download/delete a file to/from PRO Storage
            UploadAndDownLoadAndDeleteFile(server, program.environment, program.username, program.applicationName, program.applicationVersion);

        } catch (Exception e) {
            logger.error("Could not execute program", e);
        } finally {
            if (server != null) {
                server.closeConnection();
            }
        }

    }

    private static void CreateMultidimensionalParameter() {
        // create a new two dimensional parameter
        Parameter transport = new Parameter(2);

        transport.add(new String[]{"New York", "Rome"}, 235.0);
        transport.add(new String[]{"Berlin", "Washington"}, 385.0);
        transport.add(new String[]{"Amsterdam", "Paris"}, 965.0);
        transport.add(new String[]{"New York", "Paris"}, 37.0);

        // iterate over the transport data
        for (TupleValuePair<Double> tv : transport.getData()) {
            logger.info("TupleValuePair: {}", tv);
        }
    }

    static void ListAllJobs(Server server) {
        logger.info("Current jobs");

        // get the current list of jobs
        Job[] jobs = server.getCurrentJobs();
        for (Job job : jobs) {
            logger.info("App={}, Description={}, Status={}, Owner={}, Created={}, RunTime={}, QueueTime={}",
                    job.getApp(), job.getDescription(), job.getStatus(), job.getOwner(), job.getCreated(), job.getRunTimeInSeconds(), job.getQueueTimeInSeconds());
        }
    }

    static void BasicScheduleJob(Server server, String applicationName, String applicationVersion) {
        // This example will queue a job at the server that starts the example application and runs the BasicExample procedure
        ProcedureCall procedureCall = new ProcedureCall("proc_BasicScheduleJob", new Parameter(100.0), new StringParameter("text"));

        // additionally we specify we want to run it one minute from now; by default PRO will try to run it immediately
        Date scheduleAt = new Date(System.currentTimeMillis() + 60*1000);

        // further we specify the timeout for the job as 1 hour
        int timeoutMillis = 60 * 60 * 1000;
        JobConfig jobConfig = new JobConfig(applicationName, applicationVersion, "BasicScheduleJob", timeoutMillis, scheduleAt, procedureCall);

        // schedule it at the PRO server
        server.scheduleJob(jobConfig);

    }

    static void ScheduleJobAndCheck(Server server, String applicationName, String applicationVersion) {
        // in this example we will schedule the same job as with the basic-example, but using default values
        ProcedureCall procedureCall = new ProcedureCall("proc_ScheduleJobAndCheck", new Parameter(200.0), new StringParameter("some other text"));
        JobConfig jobConfig = new JobConfig(applicationName, applicationVersion, "ScheduleJobAndCheck", procedureCall);

        Job job = server.scheduleJob(jobConfig);
        // let's wait for the job to finish
        while (!job.hasEnded()) {
            logger.info("Waiting for job: App={}, Description={}, Status={}, Owner={}, Created={}, RunTime={}, QueueTime={}",
                    job.getApp(), job.getDescription(), job.getStatus(), job.getOwner(), job.getCreated(), job.getRunTimeInSeconds(), job.getQueueTimeInSeconds());
            try {
                // do some stuff ... emulate by sleeping
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.error("Thread interrupted", ex);
            }

            // retrieve the latest info from server again, because
            // the state might have changed
            job.refresh();
        }
        logger.info("Ended: App={}, Description={}, Status={}, Owner={}, Created={}, RunTime={}, QueueTime={}",
                job.getApp(), job.getDescription(), job.getStatus(), job.getOwner(), job.getCreated(), job.getRunTimeInSeconds(), job.getQueueTimeInSeconds());
    }

    static void ScheduleJobAndCheckResult(Server server, String applicationName, String applicationVersion) {
        // This example is similar to the ScheduleJobAndCheck, but the interaction is now more fine-grained
        ProcedureCall procedureCall = new ProcedureCall("proc_ScheduleJobAndCheckResult", new Parameter(300.0), new StringParameter("new text"));
        JobConfig jobConfig = new JobConfig(applicationName, applicationVersion, "ScheduleJobAndCheckResult", procedureCall);

        // create a new instance of the JobInteractor, which allows us to interact with the job
        JobInteractor jobInteractor = new JobInteractor();
        Job job = server.scheduleJob(jobConfig, jobInteractor);

        InteractLoop(job, jobInteractor);
    }

    static void ReAttachJob(Server server) {
        // get the current list of jobs
        Job[] jobs = server.getCurrentJobs();

        if (jobs.length > 0) {
            // let's re-attach on the job we created with the BasicScheduleJob
            // (it was scheduled on 1 minute from its submission time)
            Job existingJob = jobs[0];
            for (Job job : jobs) {
                if ("BasicScheduleJob".equals(job.getDescription()) && !job.hasEnded()) {
                    existingJob = job;

                }
            }

            logger.info("Reattaching to: {}", existingJob);

            JobInteractor existingJobInteractor = new JobInteractor();
            existingJob.setJobInteractor(existingJobInteractor);

            // and do the same interaction loop as the one from the ScheduleJobAndCheckResult
            InteractLoop(existingJob, existingJobInteractor);
        }

    }

    static void InteractLoop(Job job, JobInteractor jobInteractor) {
        // This is an example main-loop that will wait for events to happen on the remote job.
        boolean done = false;
        boolean finishHappened = false;
        do {
            // wait for some while to capture events from the pending job
            InteractiveJobEvent ev = jobInteractor.waitForEvent(1000);

            // because it is possible that events arrive in a different order in which they happened remotely,
            // we will wait one more time for incoming ProcedureCalls. This is done by setting 'finishHappened'
            // to true at the Finished event below. When we waited one time for other potential incoming messages,
            // after receiving the finish event, we deem this job really done.
            if (finishHappened) {
                done = true;
            }
            switch (ev) {
                case TimeOut:
                    // the specified amount of time (1 second) expired, do some other stuff
                    logger.info("Waiting for events on job");
                    break;

                case Error:
                    String lastError = jobInteractor.firstIncomingError();
                    logger.error("error: {}", lastError);
                    // when we get errors, just terminate
                    job.terminate();
                    done = true;
                    break;

                case ProcedureCall:
                    ProcedureCall lastProcedureCall = jobInteractor.firstIncomingProcedureCall();
                    logger.info("procedureCall: {}", lastProcedureCall.getProcedureName());

                    // show what we retrieved
                    DisplayProcedureCall(lastProcedureCall);
                    break;

                case Finished:
                    logger.info("job Finished");
                    finishHappened = true;
                    break;
                default:
                    logger.warn("Unknown state: {}", ev);
                    break;
            }
        } while (!done);
    }

    private static void DisplayProcedureCall(ProcedureCall lastProcedureCall) {
        for (int i = 0; i < lastProcedureCall.getProcedureArguments().size(); i++) {
            Identifier id = lastProcedureCall.getProcedureArguments().get(i);
            switch (id.getIdentifierType()) {
                case Parameter:
                    Parameter par = (Parameter) id;
                    logger.info(" argument[{0}], Parameter:", i);
                    for (TupleValuePair<Double> tv : par.getData()) {
                        logger.info("  {} : {}", tv.getTuple(), tv.getValue());
                    }
                    break;
                case StringParameter:
                    StringParameter stringPar = (StringParameter) id;
                    logger.info(" argument[{}], StringParameter:", i);
                    for (TupleValuePair<String> tv : stringPar.getData()) {
                        logger.info("  {} : '{}'", tv.getTuple(), tv.getValue());
                    }
                    break;
                default:
                    logger.warn("Unknown identifier type: {}", id.getIdentifierType());
                    break;
            }
        }
    }

    static void AdvancedInteraction(Server server, String applicationName, String applicationVersion) {
        // This example is similar to the ScheduleJobAndCheckResult, but the interaction is now more fine-grained
        ProcedureCall procedureCall = new ProcedureCall("proc_AdvancedInteraction", new Parameter(3.14), new StringParameter("abacadabra"));
        JobConfig jobConfig = new JobConfig(applicationName, applicationVersion, "AdvancedInteraction", procedureCall);

        // create a new instance of the JobInteractor, which allows us to interact with the job
        JobInteractor jobInteractor = new JobInteractor();
        Job job = server.scheduleJob(jobConfig, jobInteractor);

        AdvancedInteractLoop(job, jobInteractor);
    }

    static void AdvancedInteractLoop(Job job, JobInteractor jobInteractor) {
        boolean done = false;
        boolean finishHappened = false;
        int numberOfWaits = 0;
        do {
            // wait for some while to capture events from the pending job
            InteractiveJobEvent ev = jobInteractor.waitForEvent(1000);

            // because it is possible that events arrive in a different order in which they happened remotely,
            // we will wait one more time for incoming ProcedureCalls. This is done by setting 'finishHappened'
            // to true at the Finished event below. When we waited one time for other potential incoming messages,
            // after receiving the finish event, we deem this job really done.
            if (finishHappened) {
                done = true;
            }
            switch (ev) {
                case TimeOut:
                    // show some status
                    logger.info("Waiting for events on job");
                    numberOfWaits++;
                    // after waiting some time, inject a procedureCall to give us some progress updates
                    // the AdvancedInteractProgress procedure sends (by issuing a DelegateToClient)
                    // the desired progress updates, after that it continues where it left of the main
                    // procedure
                    if (numberOfWaits % 5 == 0) {
                        logger.info("Requesting progress report");
                        ProcedureCall progressCall = new ProcedureCall("proc_AdvancedInteractProgress");
                        jobInteractor.inject(progressCall);
                    }
                    break;

                case Error:
                    String lastError = jobInteractor.firstIncomingError();
                    logger.info("error: {}", lastError);
                    // when we get errors, just terminate
                    job.terminate();
                    done = true;
                    break;

                case ProcedureCall:
                    ProcedureCall lastProcedureCall = jobInteractor.firstIncomingProcedureCall();
                    logger.info("procedureCall: {}", lastProcedureCall.getProcedureName());

                    // since we know we're only going to be called with one (scalar) parameter,
                    // take some shortcuts here:
                    Parameter p = (Parameter) lastProcedureCall.getProcedureArguments().get(0);
                    double fractionCompleted = p.getData().get(0).getValue();

                    // let's check whether we think it's ok
                    if (fractionCompleted > .75) {
                        logger.info("Percentage completed = {}%, enough, so stopping the current tast", fractionCompleted * 100.0);
                        jobInteractor.stopTask();

                        // alternatively we might just stop the solver only, and continue our normal flow
                        // for this example the solver is not running using stopSolve
                    } else {
                        logger.info("Percentage completed = {}%, not enough, continueing", fractionCompleted * 100.0);
                    }
                    break;

                case Finished:
                    logger.info("job Finished");
                    finishHappened = true;
                    break;
                default:
                    logger.warn("Unknown state: {}", ev);
                    break;
            }
        } while (!done);
    }


    private static void UploadAndDownLoadAndDeleteFile(Server server, String environment, String username, String applicationName, String applicationVersion) throws IOException {
        File originalFile = null;
        File downloadedFile = null;
        // Storage path. Can be any location in the storage that is available for write for your user
        final String storagePath = "userdata" + File.separator + environment + File.separator + username + File.separator + "storage" + File.separator + "example";
        String fileLocation = null;
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        // change this value to point to your pro-api-storage-example-input.xml file location
        if (os.contains("win")) {
            fileLocation = "c:\\Temp\\files";
        } else if (os.contains("linux")) {
            fileLocation = "/tmp";
        } else {
            String message = "Not supported OS";
            logger.warn(message);
            throw new IllegalStateException(message);
        }

        originalFile = new File(fileLocation, "pro-api-storage-example-input.xml");
        downloadedFile = new File(fileLocation, "pro-api-storage-example-output.xml");

        logger.info("Original text: {}", new String(Files.readAllBytes(originalFile.toPath())));

        logger.info("Uploading the file...");
        server.uploadLocalFileToStorage(originalFile.getAbsolutePath(), storagePath);

        logger.info("Executing the job...");
        executeDownloadAndUpload(server, storagePath, applicationName, applicationVersion);

        logger.info("Downloading the file...");
        server.downloadStorageFileToLocalFile(storagePath, downloadedFile.getAbsolutePath());

        String downloadedContent = new String(Files.readAllBytes(downloadedFile.toPath()));
        logger.info("Text in the file: {}", downloadedContent);

        logger.info("Deleting the file...");
        server.deleteFileFromStorage(storagePath);
    }

    private static void executeDownloadAndUpload(Server server, String storagePath, String applicationName, String applicationVersion) {
        ProcedureCall procedureCall = new ProcedureCall("DownloadAndUpload", new StringParameter(storagePath));
        JobConfig jobConfig = new JobConfig(applicationName, applicationVersion, "DownloadAndUpload", procedureCall);

        Job job = server.scheduleJob(jobConfig);
        // let's wait for the job to finish
        while (!job.hasEnded()) {
            logger.info("Waiting for job: App={}, Description={}, Status={}, Owner={}, Created={}, RunTime={}, QueueTime={}",
                    job.getApp(), job.getDescription(), job.getStatus(), job.getOwner(), job.getCreated(), job.getRunTimeInSeconds(), job.getQueueTimeInSeconds());

            try {
                // do some stuff ... emulate by sleeping
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.error("Thread interrupted", ex);
            }

            // retrieve the latest info from server again, because
            // the state might have changed
            job.refresh();
        }
    }

    private void parseArgs(String[] args) {
        Options options = new Options();
        options.addOption("l", true, "PRO Endpoint");
        options.addOption("e", true, "PRO Environment");
        options.addOption("u", true, "Username");
        options.addOption("p", true, "Password");
        options.addOption("a", true, "Application name");
        options.addOption("v", true, "Application version");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter help = new HelpFormatter();
            help.printHelp("examples", options);
            System.exit(-1);
        }
        endpoint = getArg(cmd, "l", DEFAULT_ENDPOINT);
        environment = getArg(cmd, "e", DEFAULT_ENVIRONMENT);
        username = getArg(cmd, "u", DEFAULT_USERNAME);
        password = getArg(cmd, "p", DEFAULT_PASSWORD);
        applicationName = getArg(cmd, "a", DEFAULT_APPLICATION_NAME);
        applicationVersion = getArg(cmd, "v", DEFAULT_APPLICATION_VERSION);
    }

    private String getArg(CommandLine cmd, String key, String defaultValue) {
        return cmd.hasOption(key)? cmd.getOptionValue(key) : defaultValue;
    }

}