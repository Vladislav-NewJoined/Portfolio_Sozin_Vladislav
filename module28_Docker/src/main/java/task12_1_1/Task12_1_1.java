package task12_1_1;

public class Task12_1_1 {
    public static void main(String[] args) {
        System.out.println("""
            Задание:\s
            Модуль 12. Базы данных и Git. Задание №1:\s
            Цель задания: формирование базового представления о Docker и его предназначении\s
                Задание:
                1. Что такое Docker?
                2. Для чего он используется?
                3. Есть ли у него альтернативы?
            \s""");

        System.out.println("""
            Ответ на вопрос 1, Что такое Docker?:
                Docker - это программное обеспечение, которое позволяет вам упаковывать, доставлять и запускать
                приложения в контейнеризированной среде. Контейнеры позволяют упаковать приложение и все его
                зависимости в единую среду (контейнер), где оно может запускаться в изолированном и независимом от
                окружения образе.
            
                К примеру, Docker позволяет вам создать контейнер, который содержит ваше приложение, библиотеки,
                зависимости и всё необходимое для его работы. Этот контейнер может быть запущен на любой машине,
                где установлен Docker, без необходимости повторного конфигурирования и установки зависимостей по
                каждому компьютеру или серверу.
            
                Docker существенно упрощает разработку и развертывание приложений, улучшает их портируемость и
                обеспечивает консистентность окружений. Docker также позволяет масштабировать приложения быстро
                и эффективно, что делает его широко используемым в различных сценариях разработки программного
                обеспечения и развертывания приложений.
            
            Ответ на вопрос 2, Для чего он используется?:
                Docker используется для упаковки, доставки и выполнения приложений в изолированных средах, называемых
                контейнерами. Он обеспечивает стандартизацию среды выполнения приложений, упрощает развертывание
                приложений и повышает их портативность и масштабируемость. В контейнере можно упаковать все
                зависимости приложения, включая библиотеки, файлы конфигурации и другие компоненты, что упрощает
                процесс управления и развертывания приложений.
            
            Ответ на вопрос 3, Есть ли у него альтернативы?:
                Да, помимо Docker существуют и другие инструменты для управления контейнерами и развертывания
                приложений в изолированных средах. Некоторые из альтернатив Docker включают в себя Kubernetes,
                Podman, LXC, OpenShift и другие. Каждый инструмент имеет свои особенности и преимущества, и выбор
                конкретного инструмента зависит от конкретных требований и задач проекта.
            \s""");

        System.out.println("""
            Примеры использования Docker и Docker.hub:\s
            \s""");

        System.out.println("""
                Команды в терминале IntelliJ IDEA для работы с Docker и Docker.hub и выполнение этих команд выглядят так: \s
                ```
                C:\\Владислав\\IdeaProjectsDrafts\\Draft_Module12_Task1_Zadaniya1-3 git:[master]
                docker

                Usage:  docker [OPTIONS] COMMAND

                A self-sufficient runtime for containers

                Common Commands:
                  run         Create and run a new container from an image
                  exec        Execute a command in a running container
                  ps          List containers
                  build       Build an image from a Dockerfile
                  pull        Download an image from a registry
                  push        Upload an image to a registry
                  images      List images
                  login       Log in to a registry
                  logout      Log out from a registry
                  search      Search Docker Hub for images
                  version     Show the Docker version information
                  info        Display system-wide information

                Management Commands:
                  builder     Manage builds
                  buildx*     Docker Buildx
                  compose*    Docker Compose
                  container   Manage containers
                  context     Manage contexts
                  debug*      Get a shell into any image or container
                  dev*        Docker Dev Environments
                  extension*  Manages Docker extensions
                  feedback*   Provide feedback, right in your terminal!
                  image       Manage images
                  init*       Creates Docker-related starter files for your project
                  manifest    Manage Docker image manifests and manifest lists
                  network     Manage networks
                  plugin      Manage plugins
                  sbom*       View the packaged-based Software Bill Of Materials (SBOM) for an image
                  scout*      Docker Scout
                  system      Manage Docker
                  trust       Manage trust on Docker images
                  volume      Manage volumes

                Swarm Commands:
                  swarm       Manage Swarm

                Commands:
                  attach      Attach local standard input, output, and error streams to a running container
                  commit      Create a new image from a container's changes
                  cp          Copy files/folders between a container and the local filesystem
                  create      Create a new container
                  diff        Inspect changes to files or directories on a container's filesystem
                  events      Get real time events from the server
                  export      Export a container's filesystem as a tar archive
                  history     Show the history of an image
                  import      Import the contents from a tarball to create a filesystem image
                  inspect     Return low-level information on Docker objects
                  kill        Kill one or more running containers
                  load        Load an image from a tar archive or STDIN
                  logs        Fetch the logs of a container
                  pause       Pause all processes within one or more containers
                  port        List port mappings or a specific mapping for the container
                  rename      Rename a container
                  restart     Restart one or more containers
                  rm          Remove one or more containers
                  rmi         Remove one or more images
                  save        Save one or more images to a tar archive (streamed to STDOUT by default)
                  start       Start one or more stopped containers
                  stats       Display a live stream of container(s) resource usage statistics
                  stop        Stop one or more running containers
                  tag         Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE
                  top         Display the running processes of a container
                  unpause     Unpause all processes within one or more containers
                  update      Update configuration of one or more containers
                  wait        Block until one or more containers stop, then print their exit codes

                Global Options:
                      --config string      Location of client config files (default
                                           "C:\\\\Users\\\\User\\\\.docker")
                  -c, --context string     Name of the context to use to connect to the
                                           daemon (overrides DOCKER_HOST env var and
                                           default context set with "docker context use")
                  -D, --debug              Enable debug mode
                  -H, --host list          Daemon socket to connect to
                  -l, --log-level string   Set the logging level ("debug", "info",
                                           "warn", "error", "fatal") (default "info")
                      --tls                Use TLS; implied by --tlsverify
                      --tlscacert string   Trust certs signed only by this CA (default
                                           "C:\\\\Users\\\\User\\\\.docker\\\\ca.pem")
                      --tlscert string     Path to TLS certificate file (default
                                           "C:\\\\Users\\\\User\\\\.docker\\\\cert.pem")
                      --tlskey string      Path to TLS key file (default
                                           "C:\\\\Users\\\\User\\\\.docker\\\\key.pem")
                      --tlsverify          Use TLS and verify the remote
                  -v, --version            Print version information and quit

                Run 'docker COMMAND --help' for more information on a command.

                For more help on how to use Docker, head to https://docs.docker.com/go/guides/
                C:\\Владислав\\IdeaProjectsDrafts\\Draft_Module12_Task1_Zadaniya1-3 git:[master]
                docker pull hello-world
                Using default tag: latest
                latest: Pulling from library/hello-world
                c1ec31eb5944: Pull complete
                Digest: sha256:1408fec50309afee38f3535383f5b09419e6dc0925bc69891e79d84cc4cdcec6
                Status: Downloaded newer image for hello-world:latest
                docker.io/library/hello-world:latest
                C:\\Владислав\\IdeaProjectsDrafts\\Draft_Module12_Task1_Zadaniya1-3 git:[master]
                docker run hello-world

                Hello from Docker!
                This message shows that your installation appears to be working correctly.

                To generate this message, Docker took the following steps:
                 1. The Docker client contacted the Docker daemon.
                 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
                    (amd64)
                 3. The Docker daemon created a new container from that image which runs the
                    executable that produces the output you are currently reading.
                 4. The Docker daemon streamed that output to the Docker client, which sent it
                    to your terminal.

                To try something more ambitious, you can run an Ubuntu container with:
                 $ docker run -it ubuntu bash

                Share images, automate workflows, and more with a free Docker ID:
                 https://hub.docker.com/

                For more examples and ideas, visit:
                 https://docs.docker.com/get-started/
                ```
            \s""");
    }
}