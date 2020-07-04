package application;

class ApplicationFactory {

    static Application createApplication(ApplicationType applicationType) {

        applicationType.printApplicationType();

        return new Application(applicationType);
    }
}