# ProMC Suite of Plugins

This repository aims to act as the home to our most popular ProMC plugins. The aim is to simplify the process
that those wanting to contribute have to go through to configure their work environment. By providing
everything in a central location, we can easily centralize dependencies, versioning, etc.

## Cloning & Building

To clone down the whole project, run the following script:

```bash
git clone --recurse-submodules git@github.com:promcteam/magemonkey-parent.git \
  && cd magemonkey-parent \
  && git submodule foreach -q --recursive \
    'git switch \
    $(git config -f $toplevel/.gitmodules submodule.$name.branch || echo master)'
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
`git remote set-url origin git@github.com:promcteam/REPO.git`
or you can run a modified version of the script from above

```bash
git clone --recurse-submodules git@github.com:promcteam/magemonkey-parent.git \
  && cd magemonkey-parent \
  && git submodule foreach -q --recursive \
    'git switch \
    $(git config -f $toplevel/.gitmodules submodule.$name.branch || echo master) \
    && git remote set-url origin git@github.com:promcteam/$name.git'
```