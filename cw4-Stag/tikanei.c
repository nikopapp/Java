#include <stdio.h>
#include <locale.h>

int main (void)
{
  char *locale;
  locale = setlocale(LC_ALL, "δ");
  printf("%c\n%c\n",locale,'말');
  return 0;
}
