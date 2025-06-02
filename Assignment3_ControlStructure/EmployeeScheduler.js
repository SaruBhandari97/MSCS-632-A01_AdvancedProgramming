const readline = require("readline");
const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

const DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
const SHIFTS = ["Morning", "Afternoon", "Evening"];

let employees = [];
let schedule = {};

//Initialize the Schedule
DAYS.forEach((day) => {
  schedule[day] = {
    Morning: [],
    Afternoon: [],
    Evening: [],
  };
});


function ask(question) {
  return new Promise((resolve) => rl.question(question, resolve));
}


async function collectInput() {
  const num = parseInt(await ask("Enter number of employees: "));
  for (let i = 0; i < num; i++) {
    const name = await ask(`Enter employee name: `);
    let emp = { name, preferences: {}, daysWorked: 0 };

    for (const day of DAYS) {
      const pref = await ask(`Enter ranked shift preferences for ${name} on ${day} (e.g., Morning,Evening,Afternoon): `);
      emp.preferences[day] = pref.split(",").map((s) => s.trim());
    }

    employees.push(emp);
  }
}

//Generate the schedule
function generateSchedule() {
  for (const day of DAYS) {
    for (const emp of employees) {
      if (emp.daysWorked >= 5) continue;

      let assigned = false;
      const rankedPrefs = emp.preferences[day];

      for (const shift of rankedPrefs) {
        if (schedule[day][shift].length < 2 && !schedule[day][shift].includes(emp.name)) {
          schedule[day][shift].push(emp.name);
          emp.daysWorked++;
          assigned = true;
          break;
        }
      }

      //Resolving conflict in schedule by trying future days if all shifts are full
      if (!assigned) {
        for (const nextDay of DAYS) {
          if (nextDay === day || emp.daysWorked >= 5) continue;
          const fallbackPrefs = emp.preferences[nextDay] || [];
          for (const shift of fallbackPrefs) {
            if (schedule[nextDay][shift].length < 2 && !schedule[nextDay][shift].includes(emp.name)) {
              schedule[nextDay][shift].push(emp.name);
              emp.daysWorked++;
              assigned = true;
              break;
            }
          }
          if (assigned) break;
        }
      }
    }
  }
}

// Ensure each shift per day has at least 2 employees
function ensureMinimumStaffing() {
  for (const day of DAYS) {
    for (const shift of SHIFTS) {
      while (schedule[day][shift].length < 2) {
        const available = employees.filter(
          (e) =>
            !schedule[day][shift].includes(e.name) &&
            e.daysWorked < 5 &&
            !isAlreadyScheduledOnDay(day, e.name)
        );

        if (available.length === 0) {
          console.log(`⚠️  Could not assign enough employees for ${day} ${shift}`);
          break;
        }

        const randomEmp = available[Math.floor(Math.random() * available.length)];
        schedule[day][shift].push(randomEmp.name);
        randomEmp.daysWorked++;
      }
    }
  }
}

// Helper to check if employee already assigned on a specific day
function isAlreadyScheduledOnDay(day, employeeName) {
  return SHIFTS.some((shift) => schedule[day][shift].includes(employeeName));
}



function printSchedule() {
  console.log("\nFinal Weekly Schedule:");
  for (const day of DAYS) {
    console.log(`${day}:`);
    for (const shift of SHIFTS) {
      const assigned = schedule[day][shift];
      console.log(`  ${shift}: ${assigned.join(", ")}`);
    }
  }
}


(async function () {
  await collectInput();
  generateSchedule();
  ensureMinimumStaffing();
  printSchedule();
  rl.close();
})();
