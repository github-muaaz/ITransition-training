const crypto = require('crypto');
const fs = require('fs');
const path = require('path');
const { promisify } = require('util');

const readdir = promisify(fs.readdir);
const readFile = promisify(fs.readFile);

async function calculateHashes() {
  try {
    const folderPath = './task2';
    const files = await readdir(folderPath);
    const hashes = [];

    for (const file of files) {
      const filePath = path.join(folderPath, file);
      const data = await readFile(filePath);
      const hash = crypto.createHash('sha3-256').update(data).digest('hex');
      hashes.push(hash);
    }

    const sortedHashes = hashes.sort();
    const concatenatedHashes = sortedHashes.join('');
    const email = 'muhammadaziz.erg.1229@gmail.com';
    const finalString = concatenatedHashes + email.toLowerCase();

    const resultHash = crypto.createHash('sha3-256').update(finalString).digest('hex');
    
    console.log(resultHash);
  } catch (error) {
    console.error('Error:', error);
  }
}

calculateHashes();
