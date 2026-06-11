# DevOps Project 010 — CI/CD Pipeline with Jenkins, SonarQube, Docker, ArgoCD & EKS

![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![ArgoCD](https://img.shields.io/badge/ArgoCD-EF7B4D?style=for-the-badge&logo=argo&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

## 📌 Project Overview
End-to-end DevOps CI/CD pipeline for a Java-based User Registration Web Application. The pipeline automates code build, quality analysis, Docker image creation, and GitOps-based deployment to Amazon EKS using ArgoCD.

## 🏗️ Architecture
```
GitHub Push → Jenkins CI → Maven Build → SonarQube → Quality Gate → Docker Build → DockerHub Push → GitOps Repo Update → ArgoCD → EKS Deploy
```

## 🛠️ Tools & Technologies
| Tool | Version | Purpose |
|------|---------|---------|
| Jenkins | 2.555.2 LTS | CI/CD Orchestration |
| Maven | 3.9.16 | Java Build Tool |
| SonarQube | 26.6 Community | Code Quality Analysis |
| Docker | 29.5.3 | Containerization |
| DockerHub | - | Container Registry |
| ArgoCD | v3.4.3 | GitOps Continuous Delivery |
| Amazon EKS | - | Kubernetes Cluster |
| eksctl | 0.227.0 | EKS Cluster Management |
| kubectl | v1.36.1 | Kubernetes CLI |
| Java | OpenJDK 21 | Runtime Environment |

## 🖥️ Infrastructure
| Server | Instance Type | Purpose |
|--------|--------------|---------|
| Jenkins-Master | t2.micro | Jenkins Server |
| Jenkins-Agent | t2.micro | Build Agent (Docker, Maven) |
| SonarQube-Server | t2.medium | Code Quality Analysis |
| EKS-Bootstrap-Server | t2.micro | eksctl + kubectl + ArgoCD |
| EKS Cluster | 2x t2.medium nodes | Kubernetes Workloads |

## 📁 Repository Structure
```
DevOps-Project-010/          # Application Source Code
├── src/
│   └── main/
│       ├── java/com/example/
│       │   └── RegisterServlet.java
│       └── webapp/
│           ├── index.jsp
│           └── WEB-INF/
│               └── web.xml
├── pom.xml
├── Dockerfile
└── Jenkinsfile

DevOps-Project-010-gitops/   # GitOps Manifests
└── deployment.yaml          # Kubernetes Deployment + Service
```

## 🔄 CI/CD Pipeline Stages
1. **Cleanup Workspace** — Fresh workspace for every build
2. **Checkout from SCM** — Pull latest code from GitHub
3. **Build Application** — Maven clean package
4. **Test Application** — Maven test
5. **SonarQube Analysis** — Static code analysis
6. **Quality Gate** — Pass/Fail based on SonarQube results
7. **Build Docker Image** — Build image with Tomcat base
8. **Push Docker Image** — Push to DockerHub with build tag
9. **Update GitOps Repo** — Update image tag in deployment.yaml
10. **ArgoCD Sync** — Auto-deploy to EKS

## 🚀 Application
**User Registration App** — Java Servlet + JSP on Apache Tomcat 10.1

### Features:
- User Registration Form (Username, Email, Password)
- Registration Success Page
- Deployed as WAR on Tomcat

## ⚙️ Jenkins Configuration
### Plugins Installed:
- Eclipse Temurin Installer
- SonarQube Scanner
- Docker + Docker Pipeline
- Kubernetes + Kubernetes CLI
- Multibranch Scan Webhook Trigger

### Credentials Required:
| ID | Type | Purpose |
|----|------|---------|
| github-token | Username with Password | GitHub Access |
| dockerhub-token | Username with Password | DockerHub Access |
| sonar-token | Secret Text | SonarQube Authentication |
| jenkins-agent | SSH Username with Private Key | Agent Connection |

## 📊 SonarQube Quality Gate
- Security Analysis ✅
- Reliability Analysis ✅
- Maintainability Analysis ✅
- Code Coverage Report ✅

## 🐳 Docker Image
```bash
docker pull hmurafique93/register-app:latest
```

## ☸️ Kubernetes Deployment
```bash
# Check pods
kubectl get pods

# Check service
kubectl get svc

# Access application
http://<EXTERNAL-IP>
```

## 📝 GitOps Flow
1. Jenkins pipeline builds and pushes Docker image
2. Pipeline updates image tag in `DevOps-Project-010-gitops/deployment.yaml`
3. ArgoCD detects change in GitOps repo
4. ArgoCD automatically syncs and deploys to EKS

## 🔧 Setup Instructions

### 1. Launch EC2 Servers
Launch 4 EC2 servers as per infrastructure table above.

### 2. Configure Jenkins
- Install Java 21 + Jenkins on Jenkins-Master
- Install Java 21 + Docker on Jenkins-Agent
- Configure Master-Agent SSH connection
- Install required plugins
- Add credentials

### 3. Configure SonarQube
- Install Java 21 + SonarQube 26.6 on SonarQube-Server
- Generate authentication token
- Configure webhook for Jenkins

### 4. Create EKS Cluster
```bash
eksctl create cluster \
  --name DevOps-Project-010 \
  --region us-east-1 \
  --node-type t2.medium \
  --nodes 2 \
  --nodes-min 2 \
  --nodes-max 4 \
  --managed
```

### 5. Install ArgoCD
```bash
kubectl create namespace argocd
kubectl apply -n argocd --server-side --force-conflicts \
  -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'
```

### 6. Configure ArgoCD Application
- Repository: `https://github.com/hmurafique/DevOps-Project-010-gitops`
- Path: `.`
- Cluster: `https://kubernetes.default.svc`
- Namespace: `default`
- Sync Policy: `Automatic`

## 👨‍💻 Author
**Hafiz Muhammad Umar Rafique**
- GitHub: [@hmurafique](https://github.com/hmurafique)
- DockerHub: [hmurafique93](https://hub.docker.com/u/hmurafique93)
