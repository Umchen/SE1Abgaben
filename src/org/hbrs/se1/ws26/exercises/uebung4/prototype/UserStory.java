package org.hbrs.se1.ws26.exercises.uebung4.prototype;

    public class UserStory  {

        private String titel;
        private int id = 0;
        private double prio = 0.0;
        private String project;
        private String acceptCriteria;

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public UserStory(int id, String titel, double prio, String project, String acceptCriteria) {
            this.id = id;
            this.titel = titel;
            this.prio = prio;
            this.project = project;
            this.acceptCriteria = acceptCriteria;
        }

        public UserStory() {
        }

        public double getPrio() {
            return prio;
        }

        public void setPrio(double prio) {
            this.prio = prio;
        }

        public String getTitel() {
            return titel;
        }

        public void setTitel(String titel) {
            this.titel = titel;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAcceptCriteria() {
            return acceptCriteria;
        }

        public void setAcceptCriteria(String acceptCriteria) {
            this.acceptCriteria = acceptCriteria;
        }
    }




