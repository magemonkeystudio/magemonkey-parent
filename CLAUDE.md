# Git Scope in This Repository

This parent repo (`mm-parent`) wraps a collection of independent plugins, most
of which live in their own git submodules (`codex`, `divinity`, `fabled`,
`fabled-parties`, `fabled-enchants`, `fabled-quests`, `mirage`, `sapphire`,
`homestead`, `enigma`, `blueprint`, `fusion`, `genesis-parent/genesis`, etc.).

**Do very little git work at the parent-repo level.** By default, only
interact with git inside the *active* submodule(s) the user is actually
working on for the current task — not the parent repo, and not unrelated
submodules.

Concretely:

- Commits, branches, resets, and pushes should almost always happen inside a
  submodule directory (e.g. `cd codex && git ...`), not at the parent root.
- Don't run parent-level `git add`/`git commit`/branch operations unless the
  user explicitly asks for parent-repo history changes (e.g. bumping
  submodule pointers, restructuring the parent repo itself).
- Before touching parent-level git state, check `git status` at the root and
  flag anything unexpected (e.g. a large number of unpushed commits, unrelated
  file deletions/renames) rather than assuming it's part of the current task.
- When the user talks about "commits" for a plugin (e.g. "5 commits across
  codex, divinity, and fabled"), assume they mean commits *inside those
  submodules*, not commits in the parent repo's own history, unless they say
  otherwise.
