# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

    config.vm.box = "ubuntu/trusty64"
#    config.vm.network "forwarded_port", guest: 8080, host: 18080
    config.vm.network "private_network", ip: "192.168.50.10"
#    config.vm.post_up_message = "Access to Ask webapp: http://localhost:18080/"
    config.vm.post_up_message = "Access to Ask webapp: http://192.168.50.10:8080/"

    config.vm.provision "java", type: "shell" do |s|
        s.path = "provision/install-java.sh"
        s.privileged = true
    end

    config.vm.provision "maven", type: "shell" do |s|
        s.path = "provision/install-maven.sh"
        s.privileged = true
    end

    config.vm.provision "mongodb", type: "shell" do |s|
        s.path = "provision/install-mongodb.sh"
        s.privileged = true
    end

    config.vm.provision "run-ask-app", run: "always", type: "shell" do |s|
        s.path = "provision/run-app.sh"
        s.privileged = false
    end

end