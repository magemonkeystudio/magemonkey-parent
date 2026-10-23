# MageMonkey Suite of Plugins

This repository aims to act as the home to our most popular MageMonkey plugins. The aim is to simplify the process
that those wanting to contribute have to go through to configure their work environment. By providing
everything in a central location, we can easily centralize dependencies, versioning, etc.

## Cloning & Building

To clone down the whole project, run the following script:

```bash
git clone --recurse-submodules git@github.com:magemonkeystudio/magemonkey-parent.git \
  && cd magemonkey-parent \
  && git submodule foreach -q --recursive \
    'git switch \
    $(git config -f $toplevel/.gitmodules submodule.$name.branch || echo dev)'
```

This looks like a lot of code, but it simply clones down the whole architecture and makes sure that the
child branches are checked out to the appropriate branches instead of being checked out to an individual commit.

From there, one can package the entire suite by running `mvn package` -- Assuming all the appropriate GitHub
Packages authentication has been set up, everything should be pretty seamless and generate a `./ProBuilds`
directory containing all the built plugins, ready to go!

## Final Touches

*This really only applies if you plan to contribute to the project.

By default, Git automatically checks out submodules using https as opposed to SSH. SSH is more secure and
should be favored in most instances. You can go into each submodule and run
`git remote set-url origin git@github.com:magemonkeystudio/REPO.git`
or you can run a modified version of the script from above

```bash
git clone --recurse-submodules git@github.com:magemonkeystudio/magemonkey-parent.git \
  && cd magemonkey-parent \
  && git submodule foreach -q --recursive \
    'git switch \
    $(git config -f $toplevel/.gitmodules submodule.$name.branch || echo dev) \
    && git remote set-url origin git@github.com:magemonkeystudio/$name.git'
```

## Publishing the Parent POM

The parent POM (`studio.magemonkey:magemonkey-parent`) is published by the **Deploy Parent POM** GitHub Action
(`.github/workflows/deploy-parent.yml`). It only deploys this POM (`mvn -N`). It never builds or deploys the submodules.

To cut a release:

1. Set `<version>` in `pom.xml` to the new version (e.g. `1.21.11-R3`) and merge that to the branch you release from.
2. Go to **Actions → Deploy Parent POM → Run workflow**, pick that branch and run it.
   Tick **dry_run** first if you want to check the version and signing without deploying anything.
3. Plugins can then use the new version as their `<parent>`.

The version decides where it goes. A `-SNAPSHOT` version is deployed to
https://repo.travja.dev/snapshots and anything else to https://repo.travja.dev/releases.
If a release version is already published, the workflow fails and nothing is overwritten, so bump the version and run it again.
The workflow does not change the version, commit, or tag anything.
