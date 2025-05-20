def get_intervals(lst):
    # Возвращаем список списков, чтобы можно было изменять элементы
    return [[lst[i], lst[i+1]] for i in range(0, len(lst), 2)]

def merge_intervals(intervals):
    if not intervals:
        return []
    intervals = sorted(intervals)
    merged = [intervals[0]]
    for start, end in intervals[1:]:
        last_end = merged[-1][1]
        if start <= last_end:
            merged[-1][1] = max(last_end, end)  # теперь работает, т.к. merged[-1] - список
        else:
            merged.append([start, end])
    return merged

def intersect_intervals(a, b):
    i, j = 0, 0
    result = []
    while i < len(a) and j < len(b):
        start = max(a[i][0], b[j][0])
        end = min(a[i][1], b[j][1])
        if start < end:
            result.append([start, end])
        if a[i][1] < b[j][1]:
            i += 1
        else:
            j += 1
    return result

def appearance(intervals: dict[str, list[int]]) -> int:
    lesson = get_intervals(intervals['lesson'])
    pupil = merge_intervals(get_intervals(intervals['pupil']))
    tutor = merge_intervals(get_intervals(intervals['tutor']))
    pupil_in_lesson = intersect_intervals(pupil, lesson)
    tutor_in_lesson = intersect_intervals(tutor, lesson)
    both = intersect_intervals(pupil_in_lesson, tutor_in_lesson)
    return sum(end - start for start, end in both)
print("Файл solution.py был успешно запущен")
