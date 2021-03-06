def project = 'ekowcharles/jenkins-jobs-repo'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
branches.each {
  def branchName = it.name
  def jobName = "${project}-${branchName}".replaceAll('/','-')
  pipelineJob(jobName) {
    triggers {
      scm('* * * * *')
    }

    definition {
      cpsScm {
        scm {
          git("git://github.com/${project}.git", branchName)
        }

        scriptPath("Jenkinsfile")
      }
    }
  }
}
