Tune CPLEX Options from AIMMS
========================================

.. meta::
   :description: How to automatically tune CPLEX Options from within AIMMS.
   :keywords: tune, CPLEX, Options, automatically


The CPLEX solver has many options that influence the way CPLEX solves your model. For certain types of constraints and/or models, you can make an educated guess about which combination of options works best for your problem. However, sometimes you need more help.


To help you out, CPLEX has some automatic tuning options. 
In AIMMS, you can access this CPLEX tuning tool via the following two functions: :any:`GMP::Tuning::TuneMultipleMPS` and :any:`GMP::Tuning::TuneSingleGMP`.

As the names already suggest, the first function will tune the CPLEX options for a set of LP/MPS files. As an argument, you will have to specify the directory containing the LP/MPS files. The second function will tune the options based on one single GMP, which you will have to provide as an argument. 

The function :any:`GMP::Tuning::TuneSingleGMP` works like the other GMP function, so the solver to use is already known. In case of the function :any:`GMP::Tuning::TuneMultipleMPS`, you must also provide which solver to use as an argument.

In order to create the MPS files for multiple instances of your problem, you can set the project setting :menuselection:`General > MPS` under the ``CPLEX`` specific solver options to *At every solve*. Each time you solve an instance of your problem, the solver will generate a MPS file.


The other options to provide are the following:


   * ``FixedOptions``: A subset of the set :any:`AllOptions` that are to be considered unchangeable and for which the current project options will be used

   * ``ApplyTunedSettings``: A binary argument indicating whether the found tuned option settings should be set in the project options

   * ``OptionFileName``: File to which the tuned option settings should be written. This file can be imported in the Project Options screen.


To use these functions, we first need the following declarations:


.. code-block:: aimms

    Set FixedOptions {
        SubsetOf: AllOptions;
    }
    
    ElementParameter genMathProgram {
        Range: AllGeneratedMathematicalPrograms;
    }


To actually tune the solver settings, you can use the following code:

.. code-block:: aimms
    :linenos:

   !Determines which options we consider to be unchangable by CPLEX
   !It will use the current value for this setting in the project options.
   !As an example, we will forbid the tuning of the setting mip_search_strategy.

   FixedOptions := { 'CPLEX 12.3::mip_search_strategy' } ;

   !First create the GMP out of the Math Program

   genMathProgram := GMP::Instance::Generate( MP );
   
   !Then call the TuneSingleGMP function, which will try to find a good
   !combination of settings for the solver options for this instance.
   !The settings that are in the set FixedOptions will not be considered
   !for tuning.
   !The found settings will be applied directly in the project settings
   !and also will be written to the file "tuned_options_gmp.opt"

   gmp::Tuning::TuneSingleGMP(
      GMP                : genMathProgram , 
      FixedOptions       : FixedOptions , 
      ApplyTunedSettings : 1 , !Save found settings directly in project
      OptionFileName     : "tuned_options_gmp.opt" ) ; !Store found settings in this file


   !This call will try to find a combination of settings for
   !the solver options that are good for the set of MPS/LP files that 
   !are found in the subdirectory mps-files in the directory of 
   !the project. 
   !The settings that are in the set FixedOptions will not be considered
   !for tuning.
   !The found settings will be applied directly in the project settings
   !and also will be written to the file "tuned_options_gmp_mps.opt"

   gmp::Tuning::TuneMultipleMPS(
      DirectoryName      : "mps-files" , ! location of mps files, relative to project 
      Solver             : 'CPLEX 12.3' ,! Which solver to use, in this case CPLEX 12.3 
      FixedOptions       :  FixedOptions , !Consider these options unchangable. 
      ApplyTunedSettings :  1 , !Save found settings directly in project 
      OptionFileName     : "tuned_options_gmp_mps.opt" ) ;  !Store found settings in this file



.. note::
    
    Take care when tuning; always ensure that you have a few instances that are a good sample of the range of possible instances. 
    If the instances you are tuning are not representative for all possible instances, you will tune to one specific instance but 
    risk decreasing performance for all instances overall.


