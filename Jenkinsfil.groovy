node {
    stage("Pull Repo"){
        properties([parameters([string(defaultValue: '3.19.57.239', description: '''Dev: 3.19.57.239 QA: 3.15.185.26 Prod: 18.220.129.131''', name: 'Remote_instances', trim: true)])])
        git 'https://github.com/farrukh90/jenkins_april.git'
    }
    stage("Install Apache"){
        sh "ssh   ec2-user@${Remote_instances}    sudo yum install httpd -y"
    }
    stage("Create Index.html"){
        sh "scp  index.html  ec2-user@${Remote_instances}:/tmp"
    }
    stage("Move Files"){
        sh "ssh   ec2-user@${Remote_instances}    sudo mv /tmp/index.html  /var/www/html/index.html"
    }
    stage("Restart httpd"){
        sh "ssh   ec2-user@${Remote_instances} sudo systemctl restart  httpd"
    }
}
