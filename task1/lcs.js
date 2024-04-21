const longestCommonSubstring=(strings)=>{
  if (strings.length === 0) return "";
  let longest = "";
  for(let i = 0; i < strings[0].length; i++)
    for(let j = i + 1; j <= strings[0].length; j++){
      let substring = strings[0].substring(i, j);
      if(strings.every((str)=>str.includes(substring))){
        if (substring.length>=longest.length) {
          longest = substring;
        }
      }
    }
  return longest;
};

console.log(longestCommonSubstring(process.argv.slice(2)));
