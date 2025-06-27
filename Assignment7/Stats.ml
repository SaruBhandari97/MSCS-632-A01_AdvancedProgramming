let mean lst =
  let sum = List.fold_left (+) 0 lst in
  float_of_int sum /. float_of_int (List.length lst)

let median lst =
  let sorted = List.sort compare lst in
  let len = List.length sorted in
  if len mod 2 = 0 then
    let mid1 = List.nth sorted (len / 2 - 1)
    and mid2 = List.nth sorted (len / 2) in
    (float_of_int (mid1 + mid2)) /. 2.0
  else
    float_of_int (List.nth sorted (len / 2))

let mode lst =
  let freq_map = List.fold_left (fun acc x ->
    if List.mem_assoc x acc then
      let count = List.assoc x acc + 1 in
      (x, count) :: List.remove_assoc x acc
    else
      (x,1)::acc
  ) [] lst in
  let (mode_val, mode_count) = List.fold_left (fun (m_val,m_cnt) (k,v) ->
    if v > m_cnt then (k,v) else (m_val,m_cnt)
  ) (0,0) freq_map in
  (mode_val, mode_count)

let () =
  let data = [1;2;2;3;4] in
  Printf.printf "Mean: %.2f\n" (mean data);
  Printf.printf "Median: %.2f\n" (median data);
  let (m_val, m_count) = mode data in
  Printf.printf "Mode: %d (occurs %d times)\n" m_val m_count
