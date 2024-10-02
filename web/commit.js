const child_process = require("child_process");

const commitHash = child_process
  .execSync("git show -s --format=%H")
  .toString()
  .trim();

// const localBranchName = child_process
//   .execSync("git rev-parse --abbrev-ref HEAD")
//   .toString()
//   .trim();

// const branchName = child_process
//   .execSync(`git rev-parse --abbrev-ref @{upstream}`)
//   .toString()
//   .trim();

module.exports = {
  commitHash,
  // localBranchName,
  // branchName,
};
