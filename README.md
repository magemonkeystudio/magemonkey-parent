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

A release goes out automatically whenever a push to `dev` changes `pom.xml`: the workflow publishes the next R
(see below) and tags it. To publish by hand, e.g. a specific version or from another branch, go to
**Actions → Deploy Parent POM → Run workflow**, pick the branch and run it.

- Leave **version** empty to publish the next R for the Minecraft version in `spigot.version` (e.g.
  `26.2-R0.1-SNAPSHOT` → `26.2`). For example, if `26.2-R1` is the highest one tagged or published, it publishes
  `26.2-R2`. There's no need to bump the version in `pom.xml`.
- Or type a version, e.g. `26.2-R5`. A `-SNAPSHOT` version goes to https://repo.travja.dev/snapshots
  and is not tagged.
- Tick **dry_run** to see which version it would publish and check signing, without tagging or deploying anything.

A release is tagged in git (e.g. `26.2-R2`) and deployed to https://repo.travja.dev/releases. If that version is
already tagged or published, the workflow fails and nothing is overwritten. Nothing is committed: the version is
only set in the build, so `<version>` in `pom.xml` keeps whatever it has and isn't used.

Moving to a new Minecraft version only needs the usual `spigot.version` bump. The next run then publishes
`<new MC version>-R1`.
