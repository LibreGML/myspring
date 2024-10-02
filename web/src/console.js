import Vue from "vue";
import ElementUI from "element-ui";

const {
  commitHash = "",
  // localBranchName = "",
  // branchName = "",
  buildDate = "",
} = process.env;

const consoleFn = function(label, val) {
  const style1 =
    "background:#35495e ; padding: 1px; border-radius: 3px 0 0 3px;  color: #fff";
  const style2 =
    "background:#41b883 ; padding: 1px; border-radius: 0 3px 3px 0;  color: #fff";
  const style3 = "background:transparent";
  // eslint-disable-next-line no-console
  console.log(`%c ${label} %c ${val} %c`, style1, style2, style3);
};

consoleFn("Vue", `v${Vue.version}`);
consoleFn("ElementUI", `v${ElementUI.version}`);
consoleFn("CommitHash", commitHash);
// consoleFn("LocalBranchName", localBranchName);
// consoleFn("branchName", branchName);
consoleFn("BuildDate", buildDate);
