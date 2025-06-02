from solution import strict, circle_area, triangle_area, is_right_triangle

def test_circle_area():
    assert round(circle_area(2.0), 2) == 12.57  # π * 2^2 ≈ 12.57

def test_triangle_area():
    assert round(triangle_area(3.0, 4.0, 5.0), 2) == 6.00  # Герон, прямоугольный треугольник

def test_is_right_triangle():
    assert is_right_triangle(3, 4, 5) is True
    assert is_right_triangle(5, 3, 4) is True
    assert is_right_triangle(2, 2, 3) is False

def test_strict_decorator_correct():
    @strict
    def add(a: int, b: int) -> int:
        return a + b
    assert add(2, 3) == 5

def test_strict_decorator_wrong_type():
    import pytest
    @strict
    def add(a: int, b: int) -> int:
        return a + b
    with pytest.raises(TypeError):
        add(2, "3")
