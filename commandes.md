\# Exo 1 : Git et GitFlow sur l'API FabLab



\## Étape 1 : explorer

```bash

git status

git branch -a

git tag

git log --oneline --graph --all

git log --oneline develop..feature/penalites

git log --oneline develop..feature/prolongation

git diff develop feature/penalites --stat

git diff develop feature/prolongation --stat

```



\## Étape 2 : nettoyer feature/penalites

```bash

git switch feature/penalites

git config core.fileMode false

git rebase -i develop

git log --oneline --graph --all

./mvnw test

```



\## Étape 3 : fusionner dans develop, sans fast-forward

```bash

git switch develop

git merge --no-ff feature/penalites -m "Merge branch 'feature/penalites' into develop"

git merge --no-ff feature/prolongation

notepad README.md

notepad src\\main\\java\\fr\\fablab\\pret\\Pret.java

notepad src\\test\\java\\fr\\fablab\\pret\\PretTest.java

git diff --check

./mvnw test

git add README.md src/main/java/fr/fablab/pret/Pret.java src/test/java/fr/fablab/pret/PretTest.java

git commit --no-edit

git log --oneline --graph --all

```



\## Étape 4 : release/1.0.0

```bash

git switch -c release/1.0.0 develop

./mvnw versions:set "-DnewVersion=1.0.0" "-DgenerateBackupPoms=false"

git diff pom.xml

notepad CHANGELOG.md

./mvnw test

git add pom.xml CHANGELOG.md

git diff --staged --stat

git commit -m "chore(release):1.0.0"

git switch main

git merge --no-ff release/1.0.0 -m "Merge branch 'release/1.0.0'"

git tag -a v1.0.0 -m "FabLab API 1.0.0"

git switch develop

git merge --no-ff release/1.0.0 -m "Merge branch 'release/1.0.0' into develop"

git log --oneline --graph --all

```



\## Étape 5 : hotfix/1.0.1

```bash

git switch -c hotfix/1.0.1 main

notepad src\\test\\java\\fr\\fablab\\pret\\PretTest.java

./mvnw test

notepad src\\main\\java\\fr\\fablab\\pret\\Pret.java

./mvnw test

git add src/main/java/fr/fablab/pret/Pret.java src/test/java/fr/fablab/pret/PretTest.java

git commit -m "fix(pret): un pret de 15 jours est refusé"

./mvnw versions:set "-DnewVersion=1.0.1" "-DgenerateBackupPoms=false"

notepad CHANGELOG.md

git add pom.xml CHANGELOG.md

git commit -m "chrore(release): 1.0.1"

git switch main

git merge --no-ff hotfix/1.0.1 -m "Merge branch 'hotfix/1.0.1'"

git tag -a v1.0.1 -m "FabLab API 1.0.1"

git log --oneline --graph --all

```



\## Étape 6 : reporter le seul correctif dans develop

```bash

git switch develop

git log --oneline hotfix/1.0.1

git cherry-pick -x 3913de4

git show -s HEAD

./mvnw test

git log --oneline --graph --all

```



\## Rendu

```bash

git switch main

git add commandes.md

git commit -m "docs: commandes de l'exercice 1"

gh repo create fablab-api --public --source . --remote origin

git push origin --all

git push origin --tags

```



\## Historique final

```

PS C:\\EKOD\\fablab-api> git log --oneline --graph --all

\* c9c8e35 (develop) fix(pret): un pret de 15 jours est refusé

\*   1b29907 Merge branch 'release/1.0.0' into develop

|\\

| | \*   9fe0fb8 (HEAD -> main, tag: v1.0.1) Merge branch 'hotfix/1.0.1'

| | |\\

| | | \* b8fdb45 (hotfix/1.0.1) chrore(release): 1.0.1

| | | \* 3913de4 fix(pret): un pret de 15 jours est refusé

| | |/

| | \*   7b924bf (tag: v1.0.0) Merge branch 'release/1.0.0'

| | |\\

| | |/

| |/|

| \* | c220631 (release/1.0.0) chore(release):1.0.0

|/ /

\* |   a9ac243 Merge branch 'feature/prolongation' into develop

|\\ \\

| \* | 92f18bd (feature/prolongation) docs: règle de prolongation

| \* | 60f3f4a test(pret): prolongation unique, 7 jours au plus

| \* | 57f1c1c feat(pret): prolonger un prêt de 7 jours, une seule fois

\* | |   77bb660 Merge branch 'feature/penalites' into develop

|\\ \\ \\

| |/ /

|/| |

| \* | 96d3405 (feature/penalites) docs: règle des pénalités de retard

| \* | da30c17 fix (wip) : fix commit

|/ /

\* | 8ae3a16 test(pret): un prêt rendu n’est plus en retard

\* | f661441 docs: comment contribuer

|/

\* c401164 (tag: v0.9.0) chore: initialisation de l’API FabLab

```



